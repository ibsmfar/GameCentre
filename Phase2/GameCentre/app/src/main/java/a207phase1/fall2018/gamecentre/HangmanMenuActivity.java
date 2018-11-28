package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The initial activity for the Hangman game.
 */
public class HangmanMenuActivity extends AppCompatActivity {

    ArrayList<User> listOfUsers;

    String username;

    HangmanManager game;

    User user;

    public static boolean loaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_menu);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        setUser();
        game = user.hangmanManager;

        //HangmanGameActivity.myActivity = this;

        addStartButtonListener();
        addLoadButtonListener(this);
        //addSaveButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = (Button) findViewById(R.id.Play2048Btn);
        startButton.setOnClickListener(new View.OnClickListener() { /*when start button is clicked*/
            @Override
            public void onClick(View v) {
                switchToPreGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener(final Context context) {
        Button loadButton = findViewById(R.id.btnLoadHangman);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, context);
                setUser();
                game = user.hangmanManager;
                if (game != null){
                    makeToastLoadedText();
                    switchToGame();
                }
                else {
                    makeToastNoSaves();
                }
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }
    private void makeToastNoSaves() {
        Toast.makeText(this, "You don't have a saved game!", Toast.LENGTH_SHORT).show();
    }


//    /**
//     * Activate the save button.
//     */
//    private void addSaveButtonListener() {
//        Button saveButton = findViewById(R.id.btnSaveHangman);
//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveToFile(SAVE_FILENAME);
//                saveToFile(TEMP_SAVE_FILENAME);
//                makeToastSavedText();
//            }
//        });
//    }

//    /**
//     * Display that a game was saved successfully.
//     */
//    private void makeToastSavedText() {
//        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
//    }
    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * Switch to the HangmanPreNewGameActivity view to play the game.
     */
    public void switchToPreGame() {
        Intent tmp = new Intent(this, HangmanPreNewGameActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", true);
        startActivity(tmp);
    }

    public void switchToGame() {
        Intent tmp = new Intent(this, HangmanGameActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", false);
        startActivity(tmp);
    }

    void setUser(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                user = u;
            }
        }
    }



}
