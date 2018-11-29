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

public class GameLaunchActivity extends AppCompatActivity{


    EditText username;
    EditText password;
    ArrayList<User> listOfUsers;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        //loadFromFile(SavingData.USER_LIST);
        username = (EditText)findViewById(R.id.UserNameLogin);
        password = (EditText)findViewById(R.id.PasswordLogin);
        if (!areSlidingTilesGameScoresSetUp() && !areSlidingTilesUserScoresSetUp()){
            setUpSlidingTilesScoreboard();
        }
        addGoButtonListener();
        addSignUpButtonListener();

    }
    private void addSignUpButtonListener(){
        Button signupButton = findViewById(R.id.SignUpButton);
        signupButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                switchToSignUp();
            }
        });
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
    private void makeToastEmptyText() {
        Toast.makeText(this,"Username or password boxes empty",Toast.LENGTH_SHORT).show();
    }
    private void makeToastWrongPasswordText() {
        Toast.makeText(this,"Wrong password",Toast.LENGTH_SHORT).show();
    }
    private void switchToGameChoice(String username){
        Intent gameScreen = new Intent(this, GameSelectionActivity.class);
        gameScreen.putExtra("Username", username);
        startActivity(gameScreen);
    }
    private void switchToSignUp(){
        Intent signUpScreen = new Intent(this, GameRegisterActivity.class);
        startActivity(signUpScreen);
    }
    private void userNameNotExistToast(){
        Toast.makeText(this, "Username does not exist", Toast.LENGTH_SHORT).show();
    }

    private boolean isEmpty(EditText text){
        return TextUtils.isEmpty(text.getText().toString());
    }
//    private void saveData(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        SharedPreferences.Editor editor =  sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(GLC);
//        editor.putString("GameLaunchCentre", json);
//        editor.apply();
//    }
//    private void loadData(){
//        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
//        Gson gson = new Gson();
//        String json = sharedPreferences.getString("GameLaunchCentre", null);
//        Type type = new TypeToken<GameLaunchCentre>() {}.getType();
//        GLC = gson.fromJson(json, type);
//
//        if (GLC == null){
//            GLC = new GameLaunchCentre();
//        }
//
//    }
    /**
     * Load the board manager from fileName.
     *
     */
//    private void loadFromFile(String fileName) {
//
//        try {
//            InputStream inputStream = this.openFileInput(fileName);
//            if (inputStream != null) {
//                ObjectInputStream input = new ObjectInputStream(inputStream);
//                listOfUsers = (ArrayList<User>) input.readObject();
//                inputStream.close();
//            }
//            else{
//                listOfUsers = new ArrayList<>();
//            }
//        } catch (FileNotFoundException e) {
//            Log.e("login activity", "File not found: " + e.toString());
//        } catch (IOException e) {
//            Log.e("login activity", "Can not read file: " + e.toString());
//        } catch (ClassNotFoundException e) {
//            Log.e("login activity", "File contained unexpected data type: " + e.toString());
//        }
//    }
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
    boolean areSlidingTilesGameScoresSetUp(){
        SlidingTilesScoreboard gameScores = SavingData.loadFromFile(SavingData.ST_SCOREBOARD, this);
        if (gameScores != null){
            return true;
        }
        return false;
    }
    boolean areSlidingTilesUserScoresSetUp(){
        ArrayList<SlidingTilesScoreboard> userScoreList = SavingData.loadFromFile(SavingData.ST_USER_SCOREBOARD, this);
        if (userScoreList != null){
            return true;
        }
        return false;
    }
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
