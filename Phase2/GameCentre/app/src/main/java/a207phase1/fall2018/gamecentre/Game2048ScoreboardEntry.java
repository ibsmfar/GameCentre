package a207phase1.fall2018.gamecentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A scoreboard entry for the game of 2048 to be displayed
 */
public class Game2048ScoreboardEntry implements Serializable {
    /**
     * The username of the user
     */
    String username;
    /**
     * The score after a game of 2048 is finished
     */
    int score;

    /**
     * Creates a blank scoreboard entry
     */
    Game2048ScoreboardEntry(){
        this.username = "None";
        this.score = 0;
    }

    /**
     * Creates a scoreboard entry for a user
     * @param user the user
     * @param score the score of the user
     */
    Game2048ScoreboardEntry(String user, int score){
        this.username = user;
        this.score = score;
    }

    /**
     * A string representation of this entry
     * @return string representation of this entry
     */
    @Override
    @NonNull
    public String toString() {
        return "     User: " + username + "    Score: " + score;
    }

}

/**
 * A class used for sorting 2048 Scoreboard Entries in a Collection
 */
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
