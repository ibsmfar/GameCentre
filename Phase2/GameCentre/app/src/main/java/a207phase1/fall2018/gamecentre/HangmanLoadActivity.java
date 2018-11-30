package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * An activity where a user can choose to load either a regular or difficult game of Hangman
 */
public class HangmanLoadActivity extends AppCompatActivity {
    /**
     * The username of the user
     */
    String username;
    /**
     * The list of users
     */
    ArrayList<User> listOfUsers;
    /**
     * The user
     */
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_hangman_games);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if (userBundle != null) {
            username = userBundle.getString("Username");
        }

        setUpUser();
        addRegularGameButtonListener();
        addDifficultGameButtonListener();
    }
    private void addRegularGameButtonListener(){
        Button regBtn = findViewById(R.id.btnRegularGame);
        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isRegularGameNull()){
                    easySwitchToGame();
                }
                else {
                    makeNoSavedEasyGameToast();
                }
            }
        });
    }
    private void addDifficultGameButtonListener(){
        Button diffBtn = findViewById(R.id.btnDifficultGame);
        diffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isDifficultGameNull()){
                    hardSwitchToGame();
                }
                else{
                    makeNoSavedHardGameToast();
                }

            }
        });
    }

    /**
     * Find and set the user from the list of users
     */
    void setUpUser(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                user = u;
            }
        }
    }
    /**
     *
     * @return if a user has a saved game of regular difficulty
     */
    boolean isRegularGameNull(){
        return user.userHangmanManager.regularHangmanManager == null;
    }

    /**
     *
     * @return if a user has a saved game that is difficult
     */
    boolean isDifficultGameNull(){
        return user.userHangmanManager.difficultHangmanManager == null;
    }

    /**
     * Switches to the Hangman game if the user selects a regular game
     */
    private void easySwitchToGame(){
        Intent tmp = new Intent(this, HangmanGameActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", false);
        tmp.putExtra("Difficulty", true);
        startActivity(tmp);
    }
    /**
     * Switches to the Hangman game if the user selects a difficult game
     */
    private void hardSwitchToGame(){
        Intent tmp = new Intent(this, HangmanGameActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", false);
        tmp.putExtra("Difficulty", false);
        startActivity(tmp);
    }


    private void makeNoSavedEasyGameToast(){
        Toast.makeText(this, "You don't have a saved regular game!", Toast.LENGTH_SHORT).show();
    }
    private void makeNoSavedHardGameToast(){
        Toast.makeText(this, "You don't have a saved difficult game!", Toast.LENGTH_SHORT).show();
    }
}
