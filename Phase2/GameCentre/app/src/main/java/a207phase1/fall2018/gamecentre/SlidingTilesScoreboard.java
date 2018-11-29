package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class SlidingTilesScoreboard implements Serializable {

    final static String GAME = "game";
    ArrayList<SlidingTilesScoreboardEntry> threeComplexityScores = new ArrayList<>();
    ArrayList<SlidingTilesScoreboardEntry> fourComplexityScores = new ArrayList<>();
    ArrayList<SlidingTilesScoreboardEntry> fiveComplexityScores = new ArrayList<>();
    String username;
    SlidingTilesScoreboard(String username){
        this.username = username;
    }
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
