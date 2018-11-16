package a207phase1.fall2018.gamecentre;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import android.content.SharedPreferences.Editor;

import java.lang.reflect.Type;

public class GameLaunchActivity extends AppCompatActivity{


    GameLaunchCentre GLC;
    EditText username;
    EditText password;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch);

        loadData();
        username = (EditText)findViewById(R.id.UserNameLogin);
        password = (EditText)findViewById(R.id.PasswordLogin);

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
                else if(!GLC.isUsernameTaken(username.getText().toString())){
                    userNameNotExistToast();
                }
                else {
                    if (!GLC.usernameToPassword.get(username.getText().toString()).equals(password.getText().toString())) {
                        makeToastWrongPasswordText();
                    } else {
                        GLC.setUser(username.getText().toString());
                        switchToGameChoice();
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
    private void switchToGameChoice(){
        Intent gameScreen = new Intent(this, GameSelectionActivity.class);
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
    private void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor =  sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(GLC);
        editor.putString("GameLaunchCentre", json);
        editor.apply();
    }
    private void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("GameLaunchCentre", null);
        Type type = new TypeToken<GameLaunchCentre>() {}.getType();
        GLC = gson.fromJson(json, type);

        if (GLC == null){
            GLC = new GameLaunchCentre();
        }

    }





}
