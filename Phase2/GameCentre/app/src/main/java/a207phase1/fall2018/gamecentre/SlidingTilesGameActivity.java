package a207phase1.fall2018.gamecentre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class SlidingTilesGameActivity extends AppCompatActivity implements Observer {

    public static Activity myActivity;
    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;
    /**
     * The list of users to save and load from
     */
    private ArrayList<User> listOfUsers;
    /**
     * Currently logged in user
     */
    private String username;
    /**
     * The index of the boardmanager that we want
     */
    private int index;

    /***********************************************/
    /**
     * This is not primitive obsession; these are vital to making the timer run properly
     */
    public TextView timer;
    public static long MillisecondTime, StartTime, TimeBuff, UpdateTime = 0L;
    public Handler handler;
    public static int Seconds, Minutes, MilliSeconds;
    public static int firstSeconds, firstMinutes, firstMilliSeconds;
    public static long addTime = 0L;
    /***********************************************/
// Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        updateUserBoard();
        SavingData.saveToFile(SavingData.USER_LIST, this, listOfUsers);
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtiles_game);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        // get information from previous activities
        if (b != null) {
            index = b.getInt("index");
            username = b.getString("Username");
        }

        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        setUserBoard();
        setTimeVars();
        createTileButtons(this);

        addUndoButtonListener();
        addInGameSaveButtonListener(this);


        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNum_cols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);

        // Start the timer
        if (!boardManager.puzzleSolved()) {
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

                        columnWidth = displayWidth / boardManager.getBoard().getNum_cols();
                        columnHeight = displayHeight / boardManager.getBoard().getNum_rows();

                        display();
                    }
                });
    }
    private void addUndoButtonListener() {
        Button undoButton = findViewById(R.id.btUndo);
        undoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (boardManager.getMovesMade().currentMoveCounter == boardManager.getMovesMade().undoMoveCounter) {
                    makeToastOutOfUndos();
                }
                else{
                    boardManager.undo();
                }

            }
        });
    }
    private void addInGameSaveButtonListener(final Context context) {
        Button inGameSaveButton = findViewById(R.id.btSave);
        inGameSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateUserBoard();
                SavingData.saveToFile(SavingData.USER_LIST, context, listOfUsers);
                makeToastSavedText();
            }
        });
    }
    /*
     * Set up the board that needs to be displayed based on username and index
     */
    public void setUserBoard(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                boardManager = u.getBoardList().get(index);
            }
        }
    }
    /*
     * Update the user's boardmanager
     */
    public void updateUserBoard(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                boardManager.setMilliseconds(MilliSeconds);
                boardManager.setSeconds(Seconds);
                boardManager.setMinutes(Minutes);
                u.changeBoardManager(boardManager, index);
            }
        }
    }
    void setTimeVars(){
        MilliSeconds = boardManager.getMilliseconds();
        Seconds = boardManager.getSeconds();
        Minutes = boardManager.getMinutes();
    }
    /**
     * Create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != board.getNum_rows(); row++) {
            for (int col = 0; col != board.getNum_cols(); col++) {
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
            int row = nextPos / board.getNum_rows();
            int col = nextPos % board.getNum_cols();
            b.setBackgroundResource(board.getTile(row, col).getBackground());
            nextPos++;
        }
    }

//    @Override
//    protected void onPause() {
//        // TimeBuff += MillisecondTime;
//        handler.removeCallbacks(runnable);
//
//        super.onPause();
//        saveToFile(StartingActivity.TEMP_SAVE_FILENAME);
//    }


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

    private void makeToastOutOfUndos() {
        Toast.makeText(this, "Out of Undos", Toast.LENGTH_SHORT).show();
    }
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void update(Observable o, Object arg) {
        display();
    }
}
