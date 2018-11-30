package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The menu activity for the sliding puzzle tile game.
 */
public class SlidingTilesMenuActivity extends AppCompatActivity {

    /**
     * The list of users
     */
    ArrayList<User> listOfUsers;
    /**
     * the username of the current user
     */
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtiles_menu);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);

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
     * Display that a game was saved successfully.
     */
    private void noSavesText() {
        Toast.makeText(this, "You have no saves!", Toast.LENGTH_SHORT).show();
    }

    /**
     *
     * @param username of the user
     * @return if the user has saved sliding tiles games
     */
    boolean doesUserHasSaves(String username){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                return u.getBoardList().size() > 0;
            }
        }
        return false;
    }

    /**
     * Switch to SlidingTilesGameActivity to play the game.
     */
    private void switchToPreGame() {
        Intent tmp = new Intent(this, SlidingTilesPreNewGameActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

    /**
     * Switch to SlidingTilesGameLoadActivity to load games
     */
    private void switchToLoadScreen(){
        Intent tmp = new Intent(this, SlidingTilesGameLoadActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

    /**
     * Switch to SlidingTilesScoreboardActivity to view scores for the whole game
     */
    private void switchToGameScoreboard(){
        Intent tmp = new Intent(this, SlidingTilesScoreboardActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("displayGameScores?", true);
        startActivity(tmp);
    }

    /**
     * Switch to SlidingTilesScoreboardActivity to view scores for the user
     */
    private void switchToUserScoreboard(){
        Intent tmp = new Intent(this, SlidingTilesScoreboardActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("displayGameScores?", false);
        startActivity(tmp);
    }

}
