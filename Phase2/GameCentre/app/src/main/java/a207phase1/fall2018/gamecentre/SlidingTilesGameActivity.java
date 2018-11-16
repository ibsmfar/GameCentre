package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.SystemClock;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    public static Activity myActivity;
    GameLaunchCentre GLC;
    User currUser;

    //Button loadButton;
    Button undoButton;

    /**
     * Constants for swiping directions. Should be an enum, probably.
     */
    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    // variables for timer
    public TextView timer;
    public static long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    public static long LoadedTime = 0L;
    public Handler handler;
    public static int Seconds, Minutes, MilliSeconds;
    public static int firstSeconds, firstMinutes, firstMilliSeconds;
    public static long addTime = 0L;
    public static boolean loaded = false;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        if (loaded == true) {
            createTileButtonsLoaded(this);
        }
        else {
            createTileButtons(this);
        }
        setContentView(R.layout.activity_slidingtiles_game);
        loadData();
        currUser = GLC.currentUser;
        undoButton = (Button) findViewById(R.id.btUndo);

        addInGameSaveButtonListener();
        addUndoButtonListener();

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(SlidingTilesPreNewGameActivity.NUM_COLS);
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);

        // Start the timer
        if (boardManager.puzzleSolved() == false) {
            timer = findViewById(R.id.tvTimer);
            handler = new Handler();
            if (Seconds == 0 && Minutes == 0 && MilliSeconds == 0) {
                StartTime = SystemClock.uptimeMillis(); //StartTime is not 0
                addTime = StartTime;
            } else {
                StartTime = SystemClock.uptimeMillis() + MilliSeconds;
                addTime = StartTime - MilliSeconds;
                firstMilliSeconds = MilliSeconds;
                firstSeconds = Seconds;
                firstMinutes = Minutes;
            }
            handler.postDelayed(runnable, 0);
        }

        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().num_cols; //changed from Board.NUM_COLS
                        columnHeight = displayHeight / boardManager.getBoard().num_rows; //changed from Board.NUM_ROWS

                        display();
                    }
                });
    }

    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();


        List<Tile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesPreNewGameActivity.NUM_ROWS * SlidingTilesPreNewGameActivity.NUM_COLS;

        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(1, tileNum, numTiles));
        }

        Collections.shuffle(tiles);
        board = new Board(tiles);

        board.NUM_ROWS = SlidingTilesPreNewGameActivity.NUM_ROWS;
        board.NUM_COLS = SlidingTilesPreNewGameActivity.NUM_COLS;
        board.num_rows = SlidingTilesPreNewGameActivity.NUM_ROWS;
        board.num_cols = SlidingTilesPreNewGameActivity.NUM_COLS;

        //board.tiles = new Tile[SlidingTilesPreNewGameActivity.NUM_ROWS][SlidingTilesPreNewGameActivity.NUM_COLS]; //added after
        for (int row = 0; row != SlidingTilesPreNewGameActivity.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesPreNewGameActivity.NUM_COLS; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    private void createTileButtonsLoaded(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        board.NUM_ROWS = SlidingTilesPreNewGameActivity.NUM_ROWS;
        board.NUM_COLS = SlidingTilesPreNewGameActivity.NUM_COLS;
        for (int row = 0; row != boardManager.getBoard().num_rows; row++) {
            for (int col = 0; col != boardManager.getBoard().num_cols; col++) {
                Button tmp = new Button(context);
                tmp.setBackgroundResource(board.getTile(row, col).getBackground());
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / SlidingTilesPreNewGameActivity.NUM_ROWS;
            int col = nextPos % SlidingTilesPreNewGameActivity.NUM_COLS;
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        // TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);

        super.onPause();
        saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
    }

//    /**
//     * When back button is pressed
//     */

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        TimeBuff += MillisecondTime;
        handler.removeCallbacks(runnable);
    }*/

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                SlidingTilesPreNewGameActivity.NUM_COLS = boardManager.getBoard().num_rows;
                SlidingTilesPreNewGameActivity.NUM_ROWS = boardManager.getBoard().num_cols;
                Minutes = (int) input.readObject();
                Seconds = (int) input.readObject();
                MilliSeconds = (int) input.readObject();
                LoadedTime = MilliSeconds;
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(this.boardManager);
            outputStream.writeObject(Minutes);
            outputStream.writeObject(Seconds);
            outputStream.writeObject(MilliSeconds);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }

    public Runnable runnable = new Runnable() {

        public void run() {

            MillisecondTime = (SystemClock.uptimeMillis() - addTime) + firstMilliSeconds; //1

            UpdateTime = TimeBuff + MillisecondTime; //1

            Seconds = (int) ((UpdateTime + firstSeconds) / 1000); //0

            Minutes = ((Seconds + firstSeconds) / 60) + firstMinutes; //0

            Seconds = (Seconds + firstSeconds) % 60; //60

            MilliSeconds = (int) (UpdateTime % 1000); //1

            timer.setText("" + Minutes + ":"
                    + String.format("%02d", Seconds) + ":"
                    + String.format("%03d", MilliSeconds));

            handler.postDelayed(this, 0);
        }
    };

    private void addInGameSaveButtonListener() {
        Button inGameSaveButton = findViewById(R.id.btSave);
        inGameSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SlidingTilesMenuActivity.SAVE_FILENAME);
                saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    private void addUndoButtonListener() {
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Tile[][] tilesToReturn = boardManager.movesMade.getPreviousTiles();
                if (boardManager.movesMade.currentMoveCounter == boardManager.movesMade.undoMoveCounter) {
                    makeToastOutOfUndos();
                }
                else{
                    boardManager.undo();
                }

            }
        });
    }

    private void makeToastOutOfUndos() {
        Toast.makeText(this, "Out of Undos", Toast.LENGTH_SHORT).show();
    }

    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText text) {
        return TextUtils.isEmpty(text.getText().toString());
    }

    private void saveData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(GLC);
        editor.putString("GameLaunchCentre", json);
        editor.apply();
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("GameLaunchCentre", null);
        Type type = new TypeToken<GameLaunchCentre>() {
        }.getType();
        GLC = gson.fromJson(json, type);

        if (GLC == null) {
            GLC = new GameLaunchCentre();
        }
    }
}