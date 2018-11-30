package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * A scoreboard for SlidingTiles
 */
class SlidingTilesScoreboard implements Serializable {

    final static String GAME = "game";
    /**
     * The list of scores of 3 by 3 complexity
     */
    ArrayList<SlidingTilesScoreboardEntry> threeComplexityScores = new ArrayList<>();
    /**
     * The list of scores of 4 by 4 complexity
     */
    ArrayList<SlidingTilesScoreboardEntry> fourComplexityScores = new ArrayList<>();
    /**
     * The list of scores of 5 by 5 complexity
     */
    ArrayList<SlidingTilesScoreboardEntry> fiveComplexityScores = new ArrayList<>();
    /**
     * The username of user (or it might be fore the game)
     */
    String username;

    /**
     * Empty SlidingTilesScoreboard
     * @param username of the user
     */
    SlidingTilesScoreboard(String username){
        this.username = username;
    }

    /**
     * adds an entry into its relevant list of entries based on its complexity
     * @param s The SlidingTilesScoreboard Entry to be added
     * @param complexity of the entry
     */
    void addScoreboardEntry(SlidingTilesScoreboardEntry s, int complexity){
        if (complexity == 3){
            threeComplexityScores.add(s);
            Collections.sort(threeComplexityScores, new SortByTime());
            if (threeComplexityScores.size() == 4){
                threeComplexityScores.remove(3);
            }
        }
        else if(complexity == 4){
            fourComplexityScores.add(s);
            Collections.sort(fourComplexityScores, new SortByTime());
            if (fourComplexityScores.size() == 4){
                fourComplexityScores.remove(3);
            }
        }
        else{
            fiveComplexityScores.add(s);
            Collections.sort(fiveComplexityScores, new SortByTime());
            if (fiveComplexityScores.size() == 4){
                fiveComplexityScores.remove(3);
            }
        }
    }

}
