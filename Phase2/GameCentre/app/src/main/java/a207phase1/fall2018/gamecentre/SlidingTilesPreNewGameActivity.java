package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SlidingTilesPreNewGameActivity extends AppCompatActivity {
    public RadioButton threeByThree;
    public RadioButton fourByFour;
    public RadioButton fiveByFive;
    public RadioButton number;
    public RadioButton penguin;
    public RadioButton uoft;

    public RadioButton radioGridSizeButton;
    public RadioButton radioBackgroundButton;

    public RadioGroup rgGridSize;
    public RadioGroup rgBackground;

    public static int NUM_ROWS;
    public static int NUM_COLS;

    public static int picture;
    //public GameLaunchCentre GLC;

    private ArrayList<Button> tileButtons;
    ArrayList<User> listOfUsers;
    String username;
    int indexToPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SlidingTilesGameActivity.myActivity = this;
        //loadData();
        setContentView(R.layout.activity_slidingtiles_newgame);
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent gamescreen = getIntent();
        Bundle userBundle = gamescreen.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        threeByThree = findViewById(R.id.rb3x3);
        fourByFour = findViewById(R.id.rb4x4);
        fiveByFive = findViewById(R.id.rb5x5);
        number = findViewById(R.id.rbNumbers);
        penguin = findViewById(R.id.rbPenguin);
        uoft = findViewById(R.id.rbUoft);
        addNewGameButtonListener(this);
    }

    private void addNewGameButtonListener(final Context context) {
        rgGridSize = findViewById(R.id.rgGridSize);
        rgBackground = findViewById(R.id.rgBackground);
        Button startButton = findViewById(R.id.btnStart);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedGrid = rgGridSize.getCheckedRadioButtonId();
                int selectedBackground = rgBackground.getCheckedRadioButtonId();

                radioGridSizeButton = findViewById(selectedGrid);
                radioBackgroundButton = findViewById(selectedBackground);

                if (radioGridSizeButton.getText().toString().equals("3x3")) {
                    NUM_ROWS = 3;
                    NUM_COLS = 3;
                    if (radioBackgroundButton.getText().toString().equals("Numbers")) {
                        picture = 0;
                    } else if (radioBackgroundButton.getText().toString().equals("Penguin")) {
                        picture = 1;
                    } else {
                        picture = 2;
                    }
                } else if (radioGridSizeButton.getText().toString().equals("4x4")) {
                    NUM_ROWS = 4;
                    NUM_COLS = 4;
                    if (radioBackgroundButton.getText().toString().equals("Numbers")) {
                        picture = 0;
                    } else if (radioBackgroundButton.getText().toString().equals("Penguin")) {
                        picture = 1;
                    } else {
                        picture = 2;
                    }
                } else {
                    NUM_ROWS = 5;
                    NUM_COLS = 5;
                    if (radioBackgroundButton.getText().toString().equals("Numbers")) {
                        picture = 0;
                    } else if (radioBackgroundButton.getText().toString().equals("Penguin")) {
                        picture = 1;
                    } else {
                        picture = 2;
                    }
                }

                BoardManager newBoard = new BoardManager(NUM_ROWS, NUM_COLS);
                addToUser(newBoard, username);
                SavingData.saveToFile(SavingData.USER_LIST,context, listOfUsers);
                switchToGame();

            }
        });
    }
    public void addToUser(BoardManager b, String username){
        for (User u: listOfUsers){
            if(u.getUsername().equals(username)){
                u.addBoardManager(b);
                indexToPass = u.getBoardList().size() - 1; //the index of the board so it can be easily referenced later
            }
        }
    }

    public void switchToGame() {
        Intent tmp = new Intent(this, SlidingTilesGameActivity.class);
        tmp.putExtra("index", indexToPass);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

}