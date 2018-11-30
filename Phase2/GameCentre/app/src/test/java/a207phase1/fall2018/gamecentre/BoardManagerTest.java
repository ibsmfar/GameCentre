package a207phase1.fall2018.gamecentre;

import org.junit.Test;
import java.util.List;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class BoardManagerTest {

    /** The board manager for testing. */
    BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     * @return a set of tiles that are in order
     */
    public List<Tile> makeTiles() {
        List<Tile> tiles = new ArrayList<>();
        int numTiles = 3 * 3;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    private void setUpCorrect() {
        List<Tile> tiles = makeTiles();
        Board board = new Board(tiles, 3, 3);
        boardManager = new BoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether board is solvable
     */
    @Test
    public void testIsSolvable() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 3 * 3;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(SlidingTilesPreNewGameActivity.picture, tileNum, numTiles));
        }

        setUpCorrect();
        swapFirstTwoTiles();
        boolean isSolvable = boardManager.isSolvable(3, 3, 2, tiles, 9);
        assertEquals(true, isSolvable);

        List<Tile> tiles2 = new ArrayList<>();
        final int numTiles2 = 4 * 4;
        for (int tileNum = 0; tileNum != numTiles2; tileNum++) {
            tiles2.add(new Tile(SlidingTilesPreNewGameActivity.picture, tileNum, numTiles2));
        }

        setUpCorrect();
        swapFirstTwoTiles();
        boolean isSolvable2 = boardManager.isSolvable(4, 4, 3, tiles2, 16);
        assertEquals(true, isSolvable2);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertEquals(false, boardManager.puzzleSolved());
        boardManager = new BoardManager(3, 3);
        assertEquals(false, boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(8, boardManager.getBoard().getTile(2, 1).getId());
        assertEquals(9, boardManager.getBoard().getTile(2, 2).getId());
        boardManager.getBoard().swapTiles(2, 2, 2, 1);
        assertEquals(9, boardManager.getBoard().getTile(2, 1).getId());
        assertEquals(8, boardManager.getBoard().getTile(2, 2).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertEquals(true, boardManager.isValidTap(5));
        assertEquals(true, boardManager.isValidTap(7));
        assertEquals(false, boardManager.isValidTap(4));
    }

    @Test
    public void testToString() {
        setUpCorrect();
        assertEquals("A board manager with a 3 by 3 board.", boardManager.toString());
    }

    @Test
    public void testSetAndGetTime() {
        setUpCorrect();
        boardManager.setMilliseconds(20);
        boardManager.setSeconds(10);
        boardManager.setMinutes(2);
        assertEquals(boardManager.getMilliseconds(), 20);
        assertEquals(boardManager.getSeconds(), 10);
        assertEquals(boardManager.getMinutes(), 2);
    }

    @Test
    public void testBlankTileRow() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 3 * 3;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(SlidingTilesPreNewGameActivity.picture, tileNum, numTiles));
        }

        setUpCorrect();
        assertEquals(2, boardManager.blankTileRowLocation(tiles, 9));
    }

    @Test
    public void testInversions() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = 3 * 3;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(SlidingTilesPreNewGameActivity.picture, tileNum, numTiles));
        }

        setUpCorrect();
        assertEquals(0, boardManager.totalInversions(tiles, 9));
    }

    @Test
    public void testUndo() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        boardManager.touchMove(5);
        assertEquals(false, boardManager.puzzleSolved());
        boardManager.undo();
        assertEquals(true, boardManager.puzzleSolved());
    }

    @Test
    public void testTouchMove() {
        setUpCorrect();
        assertEquals(true, boardManager.puzzleSolved());
        boardManager.touchMove(5);
        assertEquals(false, boardManager.puzzleSolved());
        boardManager.touchMove(8);
        assertEquals(true, boardManager.puzzleSolved());
    }
}
