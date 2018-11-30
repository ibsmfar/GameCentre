package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
/**
 * An activity where a user can choose from a dropdown menu of games
 */
public class GameSelectionActivity extends AppCompatActivity {
    /**
     * used to create the dropdown menu
     */
    Spinner spinner;
    String spinnerSelection;
    /**
     * the username of the user
     */
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_choice);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent gameScreen = getIntent();
        Bundle userBundle = gameScreen.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        // Everything below is for setting up the dropdown menu
        spinner = findViewById(R.id.spinner);
        spinnerSelection = "default";

        ArrayList<String> list = new ArrayList<>();
        list.add("Sliding Tiles");
        list.add("Hangman");
        list.add("2048");

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item, list);

        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerSelection = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addPlayButtonListener();
    }
    private void addPlayButtonListener(){
        Button playButton = findViewById(R.id.PlayButton);
        playButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (spinnerSelection.equals("Sliding Tiles")){
                    switchToSlidingTiles();
                }
                else if (spinnerSelection.equals("Hangman")) {
                    switchToHangman();
                }
                else {
                    switchTo2048();
                }
            }
        });
    }

    /**
     * switch to the SlidingTiles menu
     */
    private void switchToSlidingTiles(){
        Intent slidingMenu = new Intent(this, SlidingTilesMenuActivity.class);
        slidingMenu.putExtra("Username", username);
        startActivity(slidingMenu);
    }
    /**
     * switch to the Hangman menu
     */
    private void switchToHangman(){
        Intent hangmanMenu = new Intent(this, HangmanMenuActivity.class);
        hangmanMenu.putExtra("Username", username);
        startActivity(hangmanMenu);
    }
    /**
     * switch to the 2048 menu
     */
    private void switchTo2048(){
        Intent menu2048 = new Intent(this, Menu2048Activity.class);
        menu2048.putExtra("Username", username);
        startActivity(menu2048);
    }
}
