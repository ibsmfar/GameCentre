package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class GameLoadActivity extends AppCompatActivity {
    ListView l;
    ArrayList<User> listOfUsers;
    ArrayList<BoardManager> userBoardManagerList;
    String username;
    int indexToPass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_load_from_saves);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }

        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        setUserBoardManagerList();


        l = findViewById(R.id.savesList);
        l.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, userBoardManagerList));

        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                BoardManager b =(BoardManager) l.getItemAtPosition(position);
                setIndexOfBoard(b);
                switchToGame();
            }
        });
    }
    private void switchToGame(){
        Intent tmp = new Intent(this, SlidingTilesGameActivity.class);
        tmp.putExtra("index", indexToPass);
        tmp.putExtra("Username", username);
        startActivity(tmp);

    }

    private void setIndexOfBoard(BoardManager b){
        for (int i = 0; i < userBoardManagerList.size(); i++){
            if (userBoardManagerList.get(i) == b){
                indexToPass = i;
            }
        }
    }

    private void setUserBoardManagerList(){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                userBoardManagerList = u.getBoardList();
            }
        }
    }
}
