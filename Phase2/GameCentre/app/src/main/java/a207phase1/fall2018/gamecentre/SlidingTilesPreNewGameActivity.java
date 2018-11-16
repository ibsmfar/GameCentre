package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class SlidingTilesPreNewGameActivity extends AppCompatActivity {
    public RadioButton threexthree;
    public RadioButton fourxfour;
    public RadioButton fivexfive;
    public RadioButton number;
    public RadioButton penguin;
    public RadioButton uoft;

    public RadioButton radioGridSizeButton;
    public RadioButton radioBackgroundButton;

    public RadioGroup rgGridSize;
    public RadioGroup rgBackground;

    public static int NUM_ROWS;
    public static int NUM_COLS;

    public int num_rows;
    public int num_cols;

    public static int picture;

    private ArrayList<Button> tileButtons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingTilesGameActivity.myActivity = this;

        setContentView(R.layout.activity_slidingtiles_newgame);
        threexthree = findViewById(R.id.rb3x3);
        fourxfour = findViewById(R.id.rb4x4);
        fivexfive = findViewById(R.id.rb5x5);
        number = findViewById(R.id.rbNumbers);
        penguin = findViewById(R.id.rbPenguin);
        uoft = findViewById(R.id.rbUoft);
        addNewGameButtonListener();
    }

    private void addNewGameButtonListener() {
        rgGridSize = findViewById(R.id.rgGridSize);
        rgBackground = findViewById(R.id.rgBackground);
        Button startButton = findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGrid = rgGridSize.getCheckedRadioButtonId();
                int selectedBackground= rgBackground.getCheckedRadioButtonId();

                radioGridSizeButton = findViewById(selectedGrid);
                radioBackgroundButton = findViewById(selectedBackground);

                if (radioGridSizeButton.getText().toString().equals("3x3")) {
                    NUM_ROWS = 3;
                    NUM_COLS = 3;
                    if (radioBackgroundButton.getText().toString().equals("Numbers")) {
                        picture = 0;
                    }
                    else if (radioBackgroundButton.getText().toString().equals("Penguin")) {
                        picture = 1;
                    }
                    else {
                        picture = 2;
                    }
                }
                else if (radioGridSizeButton.getText().toString().equals("4x4")) {
                    NUM_ROWS = 4;
                    NUM_COLS = 4;
                    if (radioBackgroundButton.getText().toString().equals("Numbers")) {
                        picture = 0;
                    }
                    else if (radioBackgroundButton.getText().toString().equals("Penguin")) {
                        picture = 1;
                    }
                    else {
                        picture = 2;
                    }
                }
                else {
                    NUM_ROWS = 5;
                    NUM_COLS = 5;
                    if (radioBackgroundButton.getText().toString().equals("Numbers")) {
                        picture = 0;
                    }
                    else if (radioBackgroundButton.getText().toString().equals("Penguin")) {
                        picture = 1;
                    }
                    else {
                        picture = 2;
                    }
                }

                SlidingTilesMenuActivity.boardManager.getBoard().NUM_COLS = NUM_COLS;
                SlidingTilesMenuActivity.boardManager.getBoard().NUM_ROWS = NUM_ROWS;
                num_rows = NUM_ROWS;
                num_cols = NUM_COLS;
                SlidingTilesMenuActivity.boardManager = new BoardManager(); //added after
                SlidingTilesMenuActivity.boardManager.getBoard().num_cols = NUM_COLS;
                SlidingTilesMenuActivity.boardManager.getBoard().num_rows = NUM_ROWS;
                switchToGame();
            }
        });
    }

    public void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesGameActivity.class);
        saveToFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(SlidingTilesMenuActivity.boardManager);
            outputStream.writeObject(SlidingTilesGameActivity.Minutes);
            outputStream.writeObject(SlidingTilesGameActivity.Seconds);
            outputStream.writeObject(SlidingTilesGameActivity.MilliSeconds);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}