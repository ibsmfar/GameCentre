package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class SlidingTilesMenuActivity extends AppCompatActivity {

    /**
     * The board manager.
     */
    ArrayList<User> listOfUsers;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtiles_menu);
        // This is for getting the username of the current user
        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        //TextView userText = findViewById(R.id.userTest);
        //userText.setText(username);
        //listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);

        addGameScoreboardButtonListener();
        addUserScoreboardButtonListener();
        addStartButtonListener();
        addLoadButtonListener();
    }

    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.StartButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToPreGame();
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(doesUserHasSaves(username)){
                    switchToLoadScreen();
                }
                noSavesText();
            }
        });
    }
    private void addGameScoreboardButtonListener(){
        Button gameScoresButton = findViewById(R.id.btnSTGameScoreboard);
        gameScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGameScoreboard();
            }
        });
    }
    private void addUserScoreboardButtonListener(){
        Button userScoresButton = findViewById(R.id.btnUserSTScores);
        userScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToUserScoreboard();
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
     * Display that a game was saved successfully.
     */
    private void noSavesText() {
        Toast.makeText(this, "You have no saves!", Toast.LENGTH_SHORT).show();
    }
    /**
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }

    boolean doesUserHasSaves(String username){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                return u.getBoardList().size() > 0;
            }
        }
        return false;
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToPreGame() {
        Intent tmp = new Intent(this, SlidingTilesPreNewGameActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

    private void switchToLoadScreen(){
        Intent tmp = new Intent(this, GameLoadActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }
    private void switchToGameScoreboard(){
        Intent tmp = new Intent(this, SlidingTilesScoreboardActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("displayGameScores?", true);
        startActivity(tmp);
    }
    private void switchToUserScoreboard(){
        Intent tmp = new Intent(this, SlidingTilesScoreboardActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("displayGameScores?", false);
        startActivity(tmp);
    }

}
