package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.xml.parsers.SAXParser;

public class HangmanLoadActivity extends AppCompatActivity {
    String username;
    ArrayList<User> listOfUsers;
    User user;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_hangman_games);

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

    void setUpUser(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                user = u;
            }
        }
    }
    boolean isRegularGameNull(){
        return user.userHangmanManager.easy == null;
    }
    boolean isDifficultGameNull(){
        return user.userHangmanManager.hard == null;
    }
    private void easySwitchToGame(){
        Intent tmp = new Intent(this, HangmanGameActivity.class);
        tmp.putExtra("Username", username);
        tmp.putExtra("NewGame", false);
        tmp.putExtra("Difficulty", true);
        startActivity(tmp);
    }
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
