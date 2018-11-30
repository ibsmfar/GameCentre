package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * An activity that displays scores for either a user or the whole game of SlidingTiles
 */
public class SlidingTilesScoreboardActivity extends AppCompatActivity {
    /**
     * The scores to be displayed
     */
    SlidingTilesScoreboard scoresToDisplay;
    /**
     * Username of the user
     */
    String username;
    /**
     * Whether to display the GameScores or
     * not (if false it will display the user scores)
     */
    boolean displayGameScores;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slidingtiles_scoreboard);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent gamescreen = getIntent();
        Bundle userBundle = gamescreen.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
            displayGameScores = userBundle.getBoolean("displayGameScores?");
        }
        if (displayGameScores){
            //displays the scores for the game
            scoresToDisplay = SavingData.loadFromFile(SavingData.ST_SCOREBOARD, this);

        }
        else{//displays the scores for the user
            ArrayList<SlidingTilesScoreboard> userScores = SavingData.loadFromFile(SavingData.ST_USER_SCOREBOARD, this);

            for (SlidingTilesScoreboard s: userScores){
                if (s.username.equals(username)){
                    scoresToDisplay = s;
                }
            }
        }
        setUpThreeByThreeScores();
        setupFourByFourScores();
        setupFiveByFiveScores();

    }

    /**
     * Setup the scores of 3by3 complexity
     */
    void setUpThreeByThreeScores(){
        TextView first3by3 = findViewById(R.id.txt3First);
        TextView second3by3 = findViewById(R.id.txt3Second);
        TextView third3by3 = findViewById(R.id.txt3Third);

        String s1 = "1. " + scoresToDisplay.threeComplexityScores.get(0).toString();
        String s2 = "2. " + scoresToDisplay.threeComplexityScores.get(1).toString();
        String s3 = "3. " + scoresToDisplay.threeComplexityScores.get(2).toString();

        first3by3.setText(s1);
        second3by3.setText(s2);
        third3by3.setText(s3);
    }
    /**
     * Setup the scores of 4by4 complexity
     */
    void setupFourByFourScores(){
        TextView first4by4 = findViewById(R.id.txt4First);
        TextView second4by4 = findViewById(R.id.txt4Second);
        TextView third4by4 = findViewById(R.id.txt4Third);

        String s1 = "1. " + scoresToDisplay.fourComplexityScores.get(0).toString();
        String s2 = "2. " + scoresToDisplay.fourComplexityScores.get(1).toString();
        String s3 = "3. " + scoresToDisplay.fourComplexityScores.get(2).toString();
        first4by4.setText(s1);
        second4by4.setText(s2);
        third4by4.setText(s3);
    }
    /**
     * Setup the scores of 5by5 complexity
     */
    void setupFiveByFiveScores(){
        TextView first5by5 = findViewById(R.id.txt5First);
        TextView second5by5 = findViewById(R.id.txt5Second);
        TextView third5by5 = findViewById(R.id.txt5Third);

        String s1 = "1. " + scoresToDisplay.fiveComplexityScores.get(0).toString();
        String s2 = "2. " + scoresToDisplay.fiveComplexityScores.get(1).toString();
        String s3 = "3. " + scoresToDisplay.fiveComplexityScores.get(2).toString();

        first5by5.setText(s1);
        second5by5.setText(s2);
        third5by5.setText(s3);
    }
}
