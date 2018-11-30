package a207phase1.fall2018.gamecentre;
//import a207phase1.fall2018.gamecentre.GameStatesContainer;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameRegisterActivity extends AppCompatActivity {
    /**
     * The list of users
     */
    ArrayList<User> listOfUsers;
    /**
     * The username that is trying to be created
     */
    EditText username;
    /**
     * The corresponding password to the username trying to be created
     */
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch_signup);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        if (listOfUsers == null){
            listOfUsers = new ArrayList<>();
        }
        username = findViewById(R.id.UsernameSignUp);
        password = findViewById(R.id.PasswordSignup);

        addRegisterButtonListener(this);

    }
    private void addRegisterButtonListener(final Context context){
        Button registerButton = findViewById(R.id.RegisterBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(username) || isEmpty(password)) {
                    makeToastEmptyText();
                }
                else if (isUsernameTaken(username.getText().toString())){
                    userNameTakenToast();
                }
                else{
                    User u = new User(username.getText().toString(), password.getText().toString());

                    if (listOfUsers == null){ //first user to ever sign up
                        listOfUsers = new ArrayList<>();
                    }
                    listOfUsers.add(u);
                    SavingData.saveToFile(SavingData.USER_LIST, context, listOfUsers);
                    setUpUserSlidingTilesScoreboard(username.getText().toString());
                    switchToSignIn();

                }
            }
        });
    }

    private void userNameTakenToast(){
        Toast.makeText(this, "Username is taken", Toast.LENGTH_SHORT).show();
    }
    private void makeToastEmptyText() {
        Toast.makeText(this,"Username or password boxes empty",Toast.LENGTH_SHORT).show();
    }
    private boolean isEmpty(EditText text){
        return TextUtils.isEmpty(text.getText().toString());
    }

    /**
     * Go back to the sign in screen when an account has been successfully created
     */
    private void switchToSignIn(){
        Intent signIn = new Intent(this, GameLaunchActivity.class);
        startActivity(signIn);
    }

    /**
     * checks if a username it already taken
     * @param username the username in question
     * @return if the username is taken
     */
    public boolean isUsernameTaken(String username){
        if (listOfUsers == null){
            return false;
        }
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                return true;
            }
        }
        return false;
    }

    /**
     * creates a SlidingTilesScoreboard for a user, fills it
     * with blank entries, and saves it.
     * @param username the username of the user
     */
    void setUpUserSlidingTilesScoreboard(String username){
        SlidingTilesScoreboard s = new SlidingTilesScoreboard(username);
        for (int i = 0; i < 3; i++){
            s.addScoreboardEntry(new SlidingTilesScoreboardEntry(username),  3);
            s.addScoreboardEntry(new SlidingTilesScoreboardEntry(username),  4);
            s.addScoreboardEntry(new SlidingTilesScoreboardEntry(username),  5);
        }

        ArrayList<SlidingTilesScoreboard> userScores = SavingData.loadFromFile(SavingData.ST_USER_SCOREBOARD, this);
        userScores.add(s);
        SavingData.saveToFile(SavingData.ST_USER_SCOREBOARD, this, userScores);
    }

}
