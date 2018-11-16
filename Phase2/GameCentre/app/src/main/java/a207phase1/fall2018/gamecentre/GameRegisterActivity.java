package a207phase1.fall2018.gamecentre;
import a207phase1.fall2018.gamecentre.GameStatesContainer;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GameRegisterActivity extends AppCompatActivity {

    GameLaunchCentre GLC;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch_signup);
        loadData();
        username = findViewById(R.id.UsernameSignUp);
        password = findViewById(R.id.PasswordSignup);

        addRegisterButtonListener();



    }
    private void addRegisterButtonListener(){
        Button registerButton = findViewById(R.id.RegisterBtn);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEmpty(username) || isEmpty(password)) {
                    makeToastEmptyText();
                }
                else if (GLC.isUsernameTaken(username.getText().toString())){
                    userNameTakenToast();
                }
                else{
                    User u = new User(username.getText().toString(), username.getText().toString());
                    GLC.addUser(u);
                    GLC.setUser(username.getText().toString());
                    saveData();
                    switchToGameChoice();

                }


            }
        });

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
    private void userNameTakenToast(){
        Toast.makeText(this, "Username is taken", Toast.LENGTH_SHORT).show();
    }
    private void makeToastEmptyText() {
        Toast.makeText(this,"Username or password boxes empty",Toast.LENGTH_SHORT).show();
    }
    private boolean isEmpty(EditText text){
        return TextUtils.isEmpty(text.getText().toString());
    }
    private void switchToGameChoice(){
        Intent gameScreen = new Intent(this, GameSelectionActivity.class);
        startActivity(gameScreen);
    }
}
