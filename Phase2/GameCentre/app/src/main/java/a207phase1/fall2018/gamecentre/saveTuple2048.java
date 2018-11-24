package a207phase1.fall2018.gamecentre;

import java.util.ArrayList;

public class saveTuple2048 {

    private int width;
    private int height;
    private long score;
    private long highScore;
    private long lastScore;
    private boolean canUndo;
    private int gameState;
    private int lastGameState;
    ArrayList<TileContainer2048> currentTiles = new ArrayList<>();
    ArrayList<TileContainer2048> undoTiles = new ArrayList<>();

    /**
     *
     * @param width of the grid being played on
     * @param height of the grid being played on
     * @param score of the current gameState
     * @param highScore highest score that has been recorded yet
     * @param lastScore of the previous move made / gameState
     * @param canUndo boolean declaring whether there is an undo left
     * @param gameState indicator of whether the game is won, lost, or ongoing
     * @param lastGameState indicator of the gameState from the last move
     */
    public saveTuple2048(int width, int height, long score, long highScore,
                         long lastScore, boolean canUndo, int gameState, int lastGameState) {
        this.width = width;
        this.height = height;
        this.score = score;
        this.highScore = highScore;
        this.lastScore = lastScore;
        this.canUndo = canUndo;
        this.gameState = gameState;
        this.lastGameState = lastGameState;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public long getScore() {
        return score;
    }

    public long getHighScore() {
        return highScore;
    }

    public long getLastScore() {
        return lastScore;
    }

    public boolean isCanUndo() {
        return canUndo;
    }

    public int getGameState() {
        return gameState;
    }

    public int getLastGameState() {
        return lastGameState;
    }

    public ArrayList<TileContainer2048> getCurrentTiles() {
        return currentTiles;
    }

    public ArrayList<TileContainer2048> getUndoTiles() {
        return undoTiles;
    }
}
