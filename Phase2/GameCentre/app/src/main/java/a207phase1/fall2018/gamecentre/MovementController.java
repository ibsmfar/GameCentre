package a207phase1.fall2018.gamecentre;

import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;


public class MovementController {

    private BoardManager boardManager = null;
    String username;

    public MovementController() {
    }

    public void setBoardManager(BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    public void setUsername(String username) {this.username = username;}

    public void processTapMovement(Context context, int position, boolean display) {

        if (boardManager.isValidTap(position)) {
            boardManager.touchMove(position);
            if (boardManager.puzzleSolved()) {
                Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
                SlidingTilesScoreboardEntry newScore = new SlidingTilesScoreboardEntry(username, boardManager.getMinutes(),
                        boardManager.getSeconds(), boardManager.getMilliseconds());
                ArrayList<SlidingTilesScoreboard> userScores = SavingData.loadFromFile(SavingData.ST_USER_SCOREBOARD, context);
                SlidingTilesScoreboard gameScores = SavingData.loadFromFile(SavingData.ST_SCOREBOARD, context);
                for (SlidingTilesScoreboard s: userScores){
                    if (s.username.equals(username)){
                        s.addScoreboardEntry(newScore, boardManager.getBoard().getNum_cols());
                    }
                }
                gameScores.addScoreboardEntry(newScore, boardManager.getBoard().getNum_cols());
                SavingData.saveToFile(SavingData.ST_USER_SCOREBOARD, context, userScores);
                SavingData.saveToFile(SavingData.ST_SCOREBOARD, context, gameScores);

            }
        } else {
            Toast.makeText(context, "Invalid Tap", Toast.LENGTH_SHORT).show();
        }
    }

}
