package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.Comparator;

public class SlidingTilesScoreboardEntry implements Serializable {
    /**
     * An entry to but put into a Scoreboard.
     * AN ENTRY SHOULD BE CREATED WHEN THE GAME IS FINISHED
     * The first element of the Scoreboard list is the name of the user
     * and the next three elements are the Minutes, Seconds, and Milliseconds.
     * This represents the score of the player in this game
     */
    String username;
    int minutes, seconds, milliseconds, totalmilliseconds;

    SlidingTilesScoreboardEntry(String username){
        this.username = username;
        this.minutes = 0;
        this.milliseconds = 0;
        this.seconds = 0;
        this.totalmilliseconds = 0;
    }

    SlidingTilesScoreboardEntry(String username, int minutes, int seconds, int milliseconds){
        this.username = username;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.totalmilliseconds = convertToMilliseconds(minutes, seconds, milliseconds);

    }

    int convertToMilliseconds(int minutes, int seconds, int milliseconds){
        return minutes * 60000 + seconds * 1000 + milliseconds;
    }
    @Override
    public String toString(){
        return username + "  " + minutes + "." + seconds + "." + milliseconds;
    }

}

class SortByTime implements Comparator<SlidingTilesScoreboardEntry>{
    public int compare(SlidingTilesScoreboardEntry a, SlidingTilesScoreboardEntry b){
        if (a.totalmilliseconds > b.totalmilliseconds){
            return 1;
        }
        else if(a.totalmilliseconds < b.totalmilliseconds){
            return -1;
        }
        else{
            return 0;
        }
    }
}



