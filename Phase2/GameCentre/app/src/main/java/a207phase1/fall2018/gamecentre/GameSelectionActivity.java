package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class GameSelectionActivity extends AppCompatActivity {
    Spinner spinner;
    String spinnerSelection;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_choice);

        Intent gamescreen = getIntent();
        Bundle userBundle = gamescreen.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }

        spinner = findViewById(R.id.spinner);
        spinnerSelection = "poop";

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
    private void switchToSlidingTiles(){
        Intent slidingMenu = new Intent(this, SlidingTilesMenuActivity.class);
        slidingMenu.putExtra("Username", username);
        startActivity(slidingMenu);
    }
    private void switchToHangman(){
        Intent hangmanMenu = new Intent(this, HangmanMenuActivity.class);
        hangmanMenu.putExtra("Username", username);
        startActivity(hangmanMenu);
    }
    private void switchTo2048(){
        Intent menu2048 = new Intent(this, Menu2048Activity.class);
        menu2048.putExtra("Username", username);
        startActivity(menu2048);
    }
}
