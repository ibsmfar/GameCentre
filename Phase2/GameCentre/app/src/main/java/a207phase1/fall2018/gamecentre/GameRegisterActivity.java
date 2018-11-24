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

    ArrayList<User> listOfUsers;
    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_launch_signup);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        if (listOfUsers == null){
            listOfUsers = new ArrayList<User>();
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
                    if (listOfUsers == null){
                        listOfUsers = new ArrayList<>();
                    }
                    listOfUsers.add(u);
                    SavingData.saveToFile(SavingData.USER_LIST, context, listOfUsers);
                    //saveToFile(SavingData.USER_LIST);
                    switchToSignIn();

                }


            }
        });

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
    private void userNameTakenToast(){
        Toast.makeText(this, "Username is taken", Toast.LENGTH_SHORT).show();
    }
    private void makeToastEmptyText() {
        Toast.makeText(this,"Username or password boxes empty",Toast.LENGTH_SHORT).show();
    }
    private boolean isEmpty(EditText text){
        return TextUtils.isEmpty(text.getText().toString());
    }

//    private void switchToGameChoice(String username){
//        Intent gameScreen = new Intent(this, GameSelectionActivity.class);
//        gameScreen.putExtra("Username", username);
//        startActivity(gameScreen);
//    }
    private void switchToSignIn(){
        Intent signIn = new Intent(this, GameLaunchActivity.class);
        startActivity(signIn);
    }
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
//    public void saveToFile(String fileName) {
//        try {
//            ObjectOutputStream outputStream = new ObjectOutputStream(
//                    this.openFileOutput(fileName, MODE_PRIVATE));
//            outputStream.writeObject(listOfUsers);
//            outputStream.close();
//        } catch (IOException e) {
//            Log.e("Exception", "File write failed: " + e.toString());
//        }
//    }
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

}
