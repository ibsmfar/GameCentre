package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.Comparator;

/**
 * A SlidingTiles Scoreboard Entry
 */
public class SlidingTilesScoreboardEntry implements Serializable {

    String username;
    int minutes, seconds, milliseconds, totalmilliseconds;

    /**
     * An empty entry
     * @param username of the user
     */
    SlidingTilesScoreboardEntry(String username){
        if (username.equals("game"))
            this.username = "-";
        else{
            this.username = username;
        }
        this.minutes = 0;
        this.milliseconds = 0;
        this.seconds = 0;
        this.totalmilliseconds = 0;
    }

    /**
     * a new entry
     * @param username of the user
     * @param minutes played
     * @param seconds played
     * @param milliseconds played
     */
    SlidingTilesScoreboardEntry(String username, int minutes, int seconds, int milliseconds){
        this.username = username;
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.totalmilliseconds = convertToMilliseconds(minutes, seconds, milliseconds);

    }

    /**
     *
     * @param minutes minutes
     * @param seconds seconds
     * @param milliseconds milliseconds
     * @return the total number of milliseconds
     */
    int convertToMilliseconds(int minutes, int seconds, int milliseconds){
        return minutes * 60000 + seconds * 1000 + milliseconds;
    }
    @Override
    public String toString(){

        return username + "  " + minutes + "." + seconds + "." + milliseconds;
    }

}

/**
 * A class for sorting SlidingTilesScoreboardEntries in a Collection
 */
class SortByTime implements Comparator<SlidingTilesScoreboardEntry>{
    public int compare(SlidingTilesScoreboardEntry a, SlidingTilesScoreboardEntry b){
        if(a.totalmilliseconds == 0 && b.totalmilliseconds == 0){
            return 0;
        }
        else if(a.totalmilliseconds == 0){
            return 1;
        }
        else if(b.totalmilliseconds == 0){
            return - 1;
        }
        else if(a.totalmilliseconds > b.totalmilliseconds){
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



