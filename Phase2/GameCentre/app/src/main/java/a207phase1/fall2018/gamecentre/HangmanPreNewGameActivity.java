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

    public RadioButton rbRegular;
    public RadioButton rbHard;

    public RadioButton radioComplexityButton;

    public RadioGroup rgComplexity;

    public static boolean Regular = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HangmanGameActivity.myActivity = this;

        setContentView(R.layout.activity_hangman_newgame);
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

    public void switchToGame() {
        Intent tmp = new Intent(this, HangmanGameActivity.class);
        saveToFile(HangmanMenuActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}