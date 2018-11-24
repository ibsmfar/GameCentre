package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private String username;
    private String password;
    private ArrayList<BoardManager> boardList;



    User(String username, String password){
        this.username = username;
        this.password = password;
        boardList = new ArrayList<>();
    }

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

