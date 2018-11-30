package a207phase1.fall2018.gamecentre;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import android.content.SharedPreferences.Editor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * The initial activity where users can either sign in or go to a page where they can sign up
 */
public class GameLaunchActivity extends AppCompatActivity{

    /**
     * The username of a user
     */
    EditText username;
    /**
     * The password of a user
     */
    EditText password;
    /**
     * The list of users
     */
    ArrayList<User> listOfUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);

        username = findViewById(R.id.UserNameLogin);
        password = findViewById(R.id.PasswordLogin);

        if (!areSlidingTilesGameScoresSetUp() && !areSlidingTilesUserScoresSetUp()){
            setUpSlidingTilesScoreboard();
        }
        addGoButtonListener();
        addSignUpButtonListener();

    }
    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        moveTaskToBack(true);
    }

    private void addGoButtonListener(){
        Button goButton = findViewById(R.id.goButt);
        goButton.setOnClickListener(new View.OnClickListener(){


            @Override
            public void onClick(View v) {

                if (isEmpty(username) || isEmpty(password)) {
                    makeToastEmptyText();
                }
                else if(!doesUsernameExist(username.getText().toString())){
                    userNameNotExistToast();
                }
                else {
                    if (!correctPassword(username.getText().toString(), password.getText().toString())) {
                        makeToastWrongPasswordText();
                    } else {
                        switchToGameChoice(username.getText().toString());
                    }
                }
            }
        });
    }
    private void addSignUpButtonListener(){
        Button signUpButton = findViewById(R.id.SignUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
    }

    /**
     * switch to the screen where users can choose a game to play
     * @param username the username of the user currently signed in
     */
    private void switchToGameChoice(String username){
        Intent gameScreen = new Intent(this, GameSelectionActivity.class);
        gameScreen.putExtra("Username", username);
        startActivity(gameScreen);
    }

    /**
     * Switch to a screen where users can sign up for an account
     */
    private void switchToSignUp(){
        Intent signUpScreen = new Intent(this, GameRegisterActivity.class);
        startActivity(signUpScreen);
    }


    private void makeToastEmptyText() {
        Toast.makeText(this,"Username or password boxes empty",Toast.LENGTH_SHORT).show();
    }
    private void makeToastWrongPasswordText() {
        Toast.makeText(this,"Wrong password",Toast.LENGTH_SHORT).show();
    }
    private void userNameNotExistToast(){
        Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
    }

    /**
     * Return if the text is empty
     * @param text the text in question
     * @return if the text is empty
     */
    private boolean isEmpty(EditText text){
        return TextUtils.isEmpty(text.getText().toString());
    }

    /**
     * returns if a user with username exists
     * @param username the username in question
     * @return if a user with username exists
     */
    public boolean doesUsernameExist(String username){
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
     * Return if the correct password has been provided for username
     * @param username the username of a user
     * @param password the password in question
     * @return if the correct password has been provided for username
     */
    public boolean correctPassword(String username, String password){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                if (u.getPassword().equals(password)){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the sliding tiles scoreboards for the game have been set up
     * @return whether or the sliding tiles scoreboards for the game have been set up
     */
    boolean areSlidingTilesGameScoresSetUp(){
        SlidingTilesScoreboard gameScores = SavingData.loadFromFile(SavingData.ST_SCOREBOARD, this);
        if (gameScores != null){
            return true;
        }
        return false;
    }
    /**
     * Checks if the sliding tiles scoreboards for the user have been set up
     * @return whether or the sliding tiles scoreboards for the user have been set up
     */
    boolean areSlidingTilesUserScoresSetUp(){
        ArrayList<SlidingTilesScoreboard> userScoreList = SavingData.loadFromFile(SavingData.ST_USER_SCOREBOARD, this);
        if (userScoreList != null){
            return true;
        }
        return false;
    }

    /**
     * 1) Creates and saves an ArrayList of SlidingTilesScoreboards for users so that when a user signs up
     * they are given their own designated blank SlidingTilesScoreboard
     * 2) Creates and saves a SlidingTilesScoreboard with blank entries for the whole game
     */
     void setUpSlidingTilesScoreboard(){
        ArrayList<SlidingTilesScoreboard> userScoreList = new ArrayList<>();
        SlidingTilesScoreboard gameScores = new SlidingTilesScoreboard(SlidingTilesScoreboard.GAME);

         for (int i = 0; i < 3; i++){
             gameScores.addScoreboardEntry(new SlidingTilesScoreboardEntry(SlidingTilesScoreboard.GAME),  3);
             gameScores.addScoreboardEntry(new SlidingTilesScoreboardEntry(SlidingTilesScoreboard.GAME),  4);
             gameScores.addScoreboardEntry(new SlidingTilesScoreboardEntry(SlidingTilesScoreboard.GAME),  5);
         }

        SavingData.saveToFile(SavingData.ST_USER_SCOREBOARD, this, userScoreList);
        SavingData.saveToFile(SavingData.ST_SCOREBOARD, this, gameScores);
     }

}
