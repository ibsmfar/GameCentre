package a207phase1.fall2018.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

public class Game2048ScoreboardEntry implements Serializable {
    String user;
    int score;

    Game2048ScoreboardEntry(){
        this.user = "None";
        this.score = 0;
    }
    Game2048ScoreboardEntry(String user, int score){
        this.user = user;
        this.score = score;
    }
    @Override
    @NonNull
    public String toString() {
        return "     User: " + user + "    Score: " + score;
    }

}
class SortByScore implements Comparator<Game2048ScoreboardEntry>{
    public int compare(Game2048ScoreboardEntry a, Game2048ScoreboardEntry b){
        if (a.score < b.score){
            return 1;
        }
        else if (a.score > b.score){
            return -1;
        }
        else{
            return 0;
        }

    }
}
