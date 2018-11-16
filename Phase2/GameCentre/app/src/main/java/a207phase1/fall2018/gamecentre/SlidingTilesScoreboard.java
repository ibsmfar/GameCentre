package a207phase1.fall2018.gamecentre;

import android.support.annotation.NonNull;

import java.util.Iterator;

public class SlidingTilesScoreboard {
    /**
     * Stores scores for either a user or a game
     * Scores[i-3] will have the top three scores for the ith complexity (3,4 or 5)
     * if userOrGame is user then ALL the scores will have the same user!
     * if userOrGame is game then ALL the scores may have different users
     * *
     */
    final static int NUM_COMPLEXITIES = 3;
    final static int NUM_SCORES = 3;
    ScoreboardEntry[][] scores = new ScoreboardEntry[NUM_COMPLEXITIES][NUM_SCORES];
    String userOrGame;

    /**
     * Return an iterator over the tiles in board.
     *
     * @return an iterator
     */

    SlidingTilesScoreboard(String userOrGame){
        this.userOrGame = userOrGame;
    }

    public void sortScore(int complexity){
        int indexToCheck = complexity - 3;

    }
    public void sortScores(ScoreboardEntry[] arr){
        int n = arr.length;
        for (int i = 0; i < n-1; i++)
            for (int j = 0; j < n-i-1; j++)
                if(arr[j] == null){
                    arr[j] = arr[j+1];
                    arr[j+1] = null;
                }
                else if(arr[j+1] == null){
                    arr[j] = arr[j]; //In other words, do nothing
                }
                else if (totalMilliseconds(arr[j]) > totalMilliseconds(arr[j+1]))
                {
                    // swap temp and arr[i]
                    ScoreboardEntry temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
    }
    public void addScore(ScoreboardEntry s){
        if (isScoreGood(s)){
            int complexity = s.complexity - 3;
            ScoreboardEntry[] fourScores = new ScoreboardEntry[4];
            for (int i = 0; i < NUM_COMPLEXITIES; i++){
                fourScores[i] = scores[complexity][i];
            }
            fourScores[3] = s;
            sortScores(fourScores);
            for (int i = 0; i < NUM_COMPLEXITIES; i++){
                scores[complexity][i] = fourScores[i];
            }
        }

    }

    /**
     * Checks if a score is better than the entries already present (if they are there)
     * @return if the scores is good
     */
    public boolean isScoreGood(ScoreboardEntry s){
        int indexTOSearch = s.complexity - 3;
        int sTime = totalMilliseconds(s);
        for (int i = 0; i < NUM_SCORES; i++){
            ScoreboardEntry currscore = scores[indexTOSearch][i];
            if (currscore != null){
                int currscoreTime = totalMilliseconds(currscore);
                if (sTime < currscoreTime){
                    return true;
                }
            }
            else
                return true;
        }
        return false;
    }
    public int totalMilliseconds(ScoreboardEntry s){
        return 60000*s.minutes + 1000*s.seconds + s.milliseconds;
    }


}
