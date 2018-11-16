package a207phase1.fall2018.gamecentre;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesMenuActivity extends AppCompatActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";
    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";
    /**
     * The board manager.
     */
    public static BoardManager boardManager; //wasn't public static before


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager();
        SlidingTilesGameActivity.myActivity = this;

        saveToFile(TEMP_SAVE_FILENAME);

        setContentView(R.layout.activity_slidingtiles_menu);
        addStartButtonListener();
        addLoadButtonListener();
        addSaveButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() { /*when start button is clicked*/
            @Override
            public void onClick(View v) {
                boardManager = new BoardManager();
                SlidingTilesGameActivity.MilliSeconds = 0;
                SlidingTilesGameActivity.Seconds = 0;
                SlidingTilesGameActivity.Minutes = 0;
                SlidingTilesGameActivity.firstMilliSeconds = 0;
                SlidingTilesGameActivity.firstSeconds = 0;
                SlidingTilesGameActivity.firstMinutes = 0;
                SlidingTilesGameActivity.loaded = false;
                switchToPreGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.btnLoad);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                load(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastLoadedText();
                SlidingTilesGameActivity.loaded = true;
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }
    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        load(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the SlidingTilesGameActivity view to play the game.
     */
    public void switchToPreGame() {
        Intent tmp = new Intent(this, SlidingTilesPreNewGameActivity.class);
        // saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    public void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesGameActivity.class);
        saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    private void load(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                boardManager = (BoardManager) input.readObject();
                SlidingTilesPreNewGameActivity.NUM_COLS = boardManager.getBoard().num_cols;
                SlidingTilesPreNewGameActivity.NUM_ROWS = boardManager.getBoard().num_rows;
                SlidingTilesGameActivity.Minutes = (int) input.readObject();
                SlidingTilesGameActivity.Seconds = (int) input.readObject();
                SlidingTilesGameActivity.MilliSeconds = (int) input.readObject();
                SlidingTilesGameActivity.LoadedTime = SlidingTilesGameActivity.MilliSeconds;
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
            outputStream.writeObject(boardManager);
            outputStream.writeObject(SlidingTilesGameActivity.Minutes);
            outputStream.writeObject(SlidingTilesGameActivity.Seconds);
            outputStream.writeObject(SlidingTilesGameActivity.MilliSeconds);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}