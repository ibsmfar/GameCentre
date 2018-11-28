package a207phase1.fall2018.gamecentre;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import java.util.ArrayList;

public class Scoreboard2048Activity extends AppCompatActivity {
    ArrayList<Game2048ScoreboardEntry> gameScores;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_2048_scoreboard);
        gameScores = SavingData.loadFromFile(SavingData.GAME_SCOREBOARD_2048, this);

        if (gameScores.get(0) != null) {
            TextView userText = findViewById(R.id.FirstPlaceText);
            String score1 = "1" + gameScores.get(0);
            userText.setText(score1);
        }
        if (gameScores.get(1) != null) {
            TextView userText = findViewById(R.id.SecondPlaceText);
            String score1 = "2" + gameScores.get(1);
            userText.setText(score1);
        }
        if (gameScores.get(2) != null) {
            TextView userText = findViewById(R.id.ThirdPlaceText);
            String score1 = "3" + gameScores.get(2);
            userText.setText(score1);
        }
        if (gameScores.get(3) != null) {
            TextView userText = findViewById(R.id.FourthPlaceText);
            String score1 = "4" + gameScores.get(3);
            userText.setText(score1);
        }
        if (gameScores.get(4) != null) {
            TextView userText = findViewById(R.id.FifthPlaceText);
            String score1 = "5" + gameScores.get(4);
            userText.setText(score1);
        }

    }

}
