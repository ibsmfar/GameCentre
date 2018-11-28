package a207phase1.fall2018.gamecentre;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Collections;

public class Main2048Activity extends AppCompatActivity {

    ArrayList<User> listOfUsers;
    //ArrayList<Game2048ScoreboardEntry> gameScores;
    String username;
    User user;

    private static final String WIDTH = "width";
    private static final String HEIGHT = "height";
    private static final String SCORE = "score";
    private static final String HIGH_SCORE = "high score temp";
    private static final String UNDO_SCORE = "undo score";
    private static final String CAN_UNDO = "can undo";
    private static final String UNDO_GRID = "undo";
    private static final String GAME_STATE = "game state";
    private static final String UNDO_GAME_STATE = "undo game state";
    private MainView2048 view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent gameScreen = getIntent();
        Bundle userBundle = gameScreen.getExtras();

        if(userBundle != null){
            username = userBundle.getString("Username");
        }
        setUser();
        view = new MainView2048(this, username);


        if (user.saveTuple2048 != null){
            load();
        }

       SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
       view.hasSaveState = settings.getBoolean("save_state", false);

       if (savedInstanceState != null) {
            if (savedInstanceState.getBoolean("hasState")) {
               load();
            }
        }
        setContentView(view);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            //Do nothing
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_DOWN) {
            view.game.move(2);
            Game2048ScoreboardEntry g = new Game2048ScoreboardEntry(username, (int) view.game.score);

            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_UP) {
            view.game.move(0);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_LEFT) {
            view.game.move(3);
            return true;
        } else if (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) {
            view.game.move(1);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putBoolean("hasState", true);
        save();
    }

    protected void onPause() {
        super.onPause();
        save();
    }

    private void save() {
        Tile2048[][] field = view.game.grid.field;
        Tile2048[][] undoField = view.game.grid.undoField;
        int width = field.length;
        int height = field.length;

        ArrayList<TileContainer2048> tiles = new ArrayList<>();
        ArrayList<TileContainer2048> undoTiles = new ArrayList<>();

        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] != null) {
                    TileContainer2048 tc = new TileContainer2048(xx, yy, field[xx][yy].getValue());
                    tiles.add(tc);
                } else {
                    TileContainer2048 tc = new TileContainer2048(xx, yy, 0);
                    tiles.add(tc);
                }

                if (undoField[xx][yy] != null) {
                    TileContainer2048 tcundo = new TileContainer2048(xx, yy, undoField[xx][yy].getValue());
                    undoTiles.add(tcundo);
                } else {
                    TileContainer2048 tcundo = new TileContainer2048(xx, yy, 0);
                    undoTiles.add(tcundo);
                }
            }
        }
        saveTuple2048 toSave = new saveTuple2048(width, height, view.game.score, view.game.highScore, view.game.lastScore,
                view.game.canUndo, view.game.gameState, view.game.lastGameState);
        toSave.currentTiles = tiles;
        toSave.undoTiles = undoTiles;

        updateUserBoard(toSave);
        SavingData.saveToFile(SavingData.USER_LIST, this, listOfUsers);

    }

    protected void onResume() {
        super.onResume();
        load();
    }

    private void load() {
        //Stopping all animations
        view.game.aGrid.cancelAnimations();
        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        setUser();
        saveTuple2048 toTake = user.saveTuple2048;

        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);

        ArrayList<TileContainer2048> currrentTiles = toTake.currentTiles;
        ArrayList<TileContainer2048> undoTiles = toTake.undoTiles;

        for (TileContainer2048 T: currrentTiles){
            if(T.getValue() > 0){
                view.game.grid.field[T.getXPosition()][T.getYPosition()] = new Tile2048(
                        T.getXPosition(), T.getYPosition(), T.getValue());
            }
            else if(T.getValue() == 0){
                view.game.grid.field[T.getXPosition()][T.getYPosition()] = null;
            }
        }

        for (TileContainer2048 T: undoTiles){
            if(T.getValue() > 0){
                view.game.grid.undoField[T.getXPosition()][T.getYPosition()] = new Tile2048(
                        T.getXPosition(), T.getYPosition(), T.getValue());
            }
            else if(T.getValue() == 0){
                view.game.grid.undoField[T.getXPosition()][T.getXPosition()] = null;
            }
        }

        view.game.score = toTake.getScore();
        view.game.highScore = toTake.getHighScore();
        view.game.lastScore = toTake.getLastScore();
        view.game.canUndo = toTake.isCanUndo();
        view.game.gameState = toTake.getGameState();
        view.game.lastGameState = toTake.getLastGameState();;
    }
    /**
     * Setting the current user of the game
     */
    void setUser() {
        for (User u : listOfUsers) {
            if (u.getUsername().equals(username))
                user = u;
        }
    }
    void updateUserBoard(saveTuple2048 s){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                u.saveTuple2048 = s;
            }
        }
    }


}
