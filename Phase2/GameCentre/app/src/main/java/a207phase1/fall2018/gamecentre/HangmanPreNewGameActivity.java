package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HangmanPreNewGameActivity extends AppCompatActivity {
    /**
     * Buttons and groups for selecting the difficult fo the game to be created
     */
    public RadioButton rbRegular;
    public RadioButton rbHard;
    public RadioButton radioComplexityButton;
    public RadioGroup rgComplexity;
    /**
     * the difficult of the game to be created
     */
    boolean Regular = false;
    /**
     * The username of the user
     */
    String username;
    /**
     * if a newGame is being created
     */
    boolean newGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HangmanGameActivity.myActivity = this;

        setContentView(R.layout.activity_hangman_newgame);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
            newGame = userBundle.getBoolean("NewGame");
        }

        rbRegular = findViewById(R.id.rbRegular);
        rbHard = findViewById(R.id.rbHard);
        addNewGameButtonListener();
    }

    private void addNewGameButtonListener() {
        rgComplexity = findViewById(R.id.rgComplexity);
        Button startButton = findViewById(R.id.btnStartHangman);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int selectedComplexity = rgComplexity.getCheckedRadioButtonId();

                radioComplexityButton = findViewById(selectedComplexity);

                if (radioComplexityButton.getText().toString().equals("Regular")) {
                    Regular = true;
                }
                else {
                    Regular = false;
                }
                switchToGame();
            }
        });
    }

    /**
     * switch to a game of Hangman
     */
    public void switchToGame() {
        Intent tmp = new Intent(this, HangmanGameActivity.class);
        //saveToFile(HangmanMenuActivity.TEMP_SAVE_FILENAME);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", true);
        tmp.putExtra("Difficulty", Regular);
        startActivity(tmp);
    }

}