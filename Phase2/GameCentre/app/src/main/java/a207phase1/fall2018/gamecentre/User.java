package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * A User that uses the app
 */
public class User implements Serializable {
    /**
     * Username of the user
     */
    private String username;
    /**
     * password of the user
     */
    private String password;
    /**
     * List of board managers for playing SlidingTiles
     */
    private ArrayList<BoardManager> boardList;
    /**
     * Manages a user's hangman games
     */
    UserHangmanManager userHangmanManager;
    /**
     * a user's saved 2048 game
     */
    saveTuple2048 saveTuple2048;



    User(String username, String password){
        this.username = username;
        this.password = password;
        boardList = new ArrayList<>();
        userHangmanManager = new UserHangmanManager();
        saveTuple2048 = new saveTuple2048();
    }

    /**
     * setters and getters
     */

    public String getUsername(){
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<BoardManager> getBoardList(){
        return boardList;
    }

    public void addBoardManager(BoardManager b){
        boardList.add(b);
    }

    public void changeBoardManager(BoardManager b, int index){
        boardList.set(index, b);
    }


}

