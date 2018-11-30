package a207phase1.fall2018.gamecentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An activity where users can check the standings for the game of Hangman
 */
public class HangmanScoreboardActivity extends AppCompatActivity {
    /**
     * The scoreboards for regular games
     */
    ArrayList<HangmanScoreboardEntry> easyGameScores;
    /**
     * The scoreboard for difficult games
     */
    ArrayList<HangmanScoreboardEntry> hardGameScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_scoreboard);
        easyGameScores = SavingData.loadFromFile(SavingData.HANGMAN_SCOREBOARD_EASY, this);
        hardGameScores = SavingData.loadFromFile(SavingData.HANGMAN_SCOREBOARD_HARD, this);

        setUpEasyGameScores();
        setUpHardGameScores();
    }

    /**
     * Set up the leaderboard for regular games
     */
    void setUpEasyGameScores(){
        TextView easyFirstText = findViewById(R.id.txtFirstHangmanEasy);
        TextView easySecondText =  findViewById(R.id.txtSecondHangmanEasy);
        TextView easyThirdText = findViewById(R.id.txtThirdHangmanEasy);

        easyFirstText.setText(easyGameScores.get(0).toString());
        easySecondText.setText(easyGameScores.get(1).toString());
        easyThirdText.setText(easyGameScores.get(2).toString());

    }

    /**
     * Set up the leaderboard for difficult games
     */
    void setUpHardGameScores(){
        TextView hardFirstText = findViewById(R.id.txtFirstHangmanHard);
        TextView hardSecondText =  findViewById(R.id.txtSecondHangmanHard);
        TextView hardThirdText = findViewById(R.id.txtThirdHangmanHard);

        hardFirstText.setText(hardGameScores.get(0).toString());
        hardSecondText.setText(hardGameScores.get(1).toString());
        hardThirdText.setText(hardGameScores.get(2).toString());

    }

}


