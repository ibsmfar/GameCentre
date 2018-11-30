package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.Comparator;

public class HangmanScoreboardEntry implements Serializable {
    String username;
    int score;

    HangmanScoreboardEntry(){
        this.username = "-";
        this.score = 0;
    }

    HangmanScoreboardEntry(String username, int score){
        this.username = username;
        this.score =  score;
    }

    @Override
    public String toString(){
        return username + " score: " + score;
    }
}

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
