package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * The starting menu for a game of hangman.
 */
public class HangmanMenuActivity extends AppCompatActivity {
    /**
     * The list of users
     */
    ArrayList<User> listOfUsers;
    /**
     * The username of the user
     */
    String username;
    /**
     * The UserHangmanManager of the user
     */
    UserHangmanManager game;
    /**
     * The current user
     */
    User user;
    /**
     * The ArrayList of HangmanScoreboardEntries that are of regular difficulty
     */
    ArrayList<HangmanScoreboardEntry> easyGameScores;
    /**
     * The ArrayList of HangmanScoreboardEntries that are difficult
     */
    ArrayList<HangmanScoreboardEntry> hardGameScores;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_menu);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        setUser();

        easyGameScores = SavingData.loadFromFile(SavingData.HANGMAN_SCOREBOARD_EASY, this);
        hardGameScores = SavingData.loadFromFile(SavingData.HANGMAN_SCOREBOARD_HARD, this);
        //sets up the Scoreboard if they have not yet been created before
        if (easyGameScores == null){
            setUpHangmanEasyScoreboard();
            SavingData.saveToFile(SavingData.HANGMAN_SCOREBOARD_EASY, this, easyGameScores);
        }
        if(hardGameScores == null){
            setUpHangmanHardScoreboard();
            SavingData.saveToFile(SavingData.HANGMAN_SCOREBOARD_HARD, this, hardGameScores);
        }

        game = user.userHangmanManager;

        addStartButtonListener();
        addLoadButtonListener(this);
        addScoreboardButtonListener();
    }

    private void addScoreboardButtonListener(){
        Button scoreboardButton = findViewById(R.id.btnHangmanLeaderboard);
        scoreboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToLeaderboard();
            }
        });
    }
    /**
     * Activate the start button.
     */
    private void addStartButtonListener() {
        Button startButton = findViewById(R.id.Play2048Btn);
        startButton.setOnClickListener(new View.OnClickListener() { /*when start button is clicked*/
            @Override
            public void onClick(View v) {
                switchToPreGame();
            }
        });
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        switchToGameSelection();
    }
    private void switchToGameSelection(){
        Intent tmp = new Intent(this, GameSelectionActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
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
                game = user.userHangmanManager;
                if (game.regularHangmanManager == null && game.difficultHangmanManager == null){
                    makeToastNoSaves();
                }
                else {
                    makeToastLoadedText();
                    switchToSaves();
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


    /**
     * Switch to the HangmanPreNewGameActivity to create and start a new game
     */
    public void switchToPreGame() {
        Intent tmp = new Intent(this, HangmanPreNewGameActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", true);
        startActivity(tmp);
    }
    /**
     * Switch to the HangmanLoadActivity to load Hangman games
     */
    public void switchToSaves(){
        Intent tmp = new Intent(this, HangmanLoadActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

    /**
     * Switch to the leaderboard
     */
    private void switchToLeaderboard(){
        Intent tmp = new Intent(this, HangmanScoreboardActivity.class);
        startActivity(tmp);
    }

    /**
     * Find and set the user from the list of users
     */
    void setUser(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                user = u;
            }
        }
    }

    /**
     * Creates the scoreboard for hangman games of regular difficulty, fills them with empty entries,
     * and saves it
     */
    void setUpHangmanEasyScoreboard(){
        easyGameScores= new ArrayList<>();
        for (int i = 0; i < 3; i ++){
            easyGameScores.add(new HangmanScoreboardEntry());
        }
        SavingData.saveToFile(SavingData.HANGMAN_SCOREBOARD_EASY, this, easyGameScores);
    }
    /**
     * Creates the scoreboard for difficult hangman games, fills them with empty entries,
     * and saves it
     */
    void setUpHangmanHardScoreboard(){
        hardGameScores = new ArrayList<>();
        for (int i = 0; i < 3; i++){
            hardGameScores.add(new HangmanScoreboardEntry());
        }
        SavingData.saveToFile(SavingData.HANGMAN_SCOREBOARD_HARD, this, hardGameScores);
    }



}
