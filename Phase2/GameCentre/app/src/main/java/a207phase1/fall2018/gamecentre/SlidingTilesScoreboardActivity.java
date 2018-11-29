package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class SlidingTilesScoreboardActivity extends AppCompatActivity {

    SlidingTilesScoreboard scoresToDisplay;
    String username;
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
            scoresToDisplay = SavingData.loadFromFile(SavingData.ST_SCOREBOARD, this);
        }
        else{
            ArrayList<SlidingTilesScoreboard> userScores = SavingData.loadFromFile(SavingData.ST_USER_SCOREBOARD, this);
            //check if userScores is null to get rid of warning
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
    void setUpThreeByThreeScores(){
        TextView first3by3 = findViewById(R.id.txt3First);
        TextView second3by3 = findViewById(R.id.txt3Second);
        TextView third3by3 = findViewById(R.id.txt3Third);

        first3by3.setText(scoresToDisplay.threeComplexityScores.get(0).toString());
        second3by3.setText(scoresToDisplay.threeComplexityScores.get(1).toString());
        third3by3.setText(scoresToDisplay.threeComplexityScores.get(2).toString());
    }
    void setupFourByFourScores(){
        TextView first4by4 = findViewById(R.id.txt4First);
        TextView second4by4 = findViewById(R.id.txt4Second);
        TextView third4by4 = findViewById(R.id.txt4Third);

        first4by4.setText(scoresToDisplay.fourComplexityScores.get(0).toString());
        second4by4.setText(scoresToDisplay.fourComplexityScores.get(1).toString());
        third4by4.setText(scoresToDisplay.fourComplexityScores.get(2).toString());
    }
    void setupFiveByFiveScores(){
        TextView first5by5 = findViewById(R.id.txt5First);
        TextView second5by5 = findViewById(R.id.txt5Second);
        TextView third5by5 = findViewById(R.id.txt5Third);

        first5by5.setText(scoresToDisplay.fiveComplexityScores.get(0).toString());
        second5by5.setText(scoresToDisplay.fiveComplexityScores.get(1).toString());
        third5by5.setText(scoresToDisplay.fiveComplexityScores.get(2).toString());
    }
}
