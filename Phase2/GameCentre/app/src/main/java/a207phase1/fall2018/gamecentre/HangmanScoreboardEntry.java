package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A scoreboard entry for a user
 */
public class HangmanScoreboardEntry implements Serializable {
    /**
     * The username of a user
     */
    String username;
    /**
     * The score of the user for a particular game of hangman
     */
    int score;

    /**
     * Creates an empty entry
     */
    HangmanScoreboardEntry(){
        this.username = "-";
        this.score = 0;
    }

    /**
     * Creates a hangman scoreboard entry for a user
     * @param username the user
     * @param score the score of the user
     */
    HangmanScoreboardEntry(String username, int score){
        this.username = username;
        this.score =  score;
    }

    /**
     * @return a string representation of this entry
     */
    @Override
    public String toString(){
        return username + " score: " + score;
    }
}

/**
 * A class for sorting HangmanScoreboard entries in a Collection
 */
class SortByHangmanScore implements Comparator<HangmanScoreboardEntry> {
    public int compare(HangmanScoreboardEntry a, HangmanScoreboardEntry b){
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
