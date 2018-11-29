package a207phase1.fall2018.gamecentre;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

public class saveTuple2048Test {
    saveTuple2048 saveTuple2048;

    @Before
    public void create(){
        int width = 0;
        int height = 0;
        int score = 0;
        long highScore = 88;
        long lastScore = 0;
        boolean canUndo = true;
        int gameState = 0;
        int lastGameState = 0;
        saveTuple2048 = new saveTuple2048(width, height, score, highScore,
                lastScore, canUndo, gameState, lastGameState);
    }
    @Test
    public void getWidthTest() {assertSame(0, saveTuple2048.getWidth());
    }

    @Test
    public void getHeightTest() {assertSame(0, saveTuple2048.getHeight()); }

    @Test
    public void getScoreTest() { assertSame(88, saveTuple2048.getScore());}

    @Test
    public void getHighScoreTest() { assertSame(88, saveTuple2048.getHighScore()); }

    @Test
    public void getLastScoreTest() {assertSame(88, saveTuple2048.getLastScore()); }

    @Test
    public void isCanUndoTest() {assertSame(true, saveTuple2048.isCanUndo()); }

    @Test
    public void getGameStateTest() {assertSame(0, saveTuple2048.getGameState()); }

    @Test
    public void getLastGameStateTest() {assertSame(0, saveTuple2048.getLastGameState()); }

}