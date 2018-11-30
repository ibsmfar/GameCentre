package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.ArrayList;

/**
 * The starting menu for the game of 2048
 */
public class Menu2048Activity extends AppCompatActivity {
    /**
     * The username of the user
     */
    String username;
    /**
     * The Scores for the game of 2048
     */
    ArrayList<Game2048ScoreboardEntry> gameScores;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_menu);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        gameScores = SavingData.loadFromFile(SavingData.GAME_SCOREBOARD_2048, this);

        if (gameScores == null){
            setUpScoreboard();
            SavingData.saveToFile(SavingData.GAME_SCOREBOARD_2048, this, gameScores);
        }

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        addPlayButtonListener();
        addScoreboardButtonListener();


    }
    private void addPlayButtonListener(){
        Button playButton = findViewById(R.id.Play2048Btn);
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switchToGame();
            }
        });
    }
    private void addScoreboardButtonListener(){
        Button scoreboardButton = findViewById(R.id.btn2048Scoreboard);
        scoreboardButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                switchToScoreboard();
            }
        });
    }

    /**
     * switch to a game of 2048
     */
    private void switchToGame(){
        Intent tmp = new Intent(this, Main2048Activity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

    /**
     * switch to the scoreboard for 2048
     */
    private void switchToScoreboard(){
        Intent tmp = new Intent(this, Scoreboard2048Activity.class);
        startActivity(tmp);
    }

    /**
     * set up the scoreboard for 2048 with blank entries and save it
     */
    private void setUpScoreboard(){
        gameScores = new ArrayList<>();
        for (int i = 0; i < 5; i ++){
            gameScores.add(new Game2048ScoreboardEntry());
        }
        SavingData.saveToFile(SavingData.GAME_SCOREBOARD_2048, this, gameScores);
    }
}


