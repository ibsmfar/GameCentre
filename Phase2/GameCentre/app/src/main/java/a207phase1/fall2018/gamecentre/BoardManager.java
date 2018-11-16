package a207phase1.fall2018.gamecentre;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, Undo {

    /**
     * The board being managed.
     */
    private Board board;


    public static Activity myActivity;
    GameLaunchCentre GLC;
    User currUser;

    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     * <p>
     * //* @param numUndos the number of allowed undos in a game with a limit on the undos
     */

    public static int picture = 0;

    BoardManager(Board board) {
        this.board = board;
    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager() {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = SlidingTilesPreNewGameActivity.NUM_ROWS * SlidingTilesPreNewGameActivity.NUM_COLS;

        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(1, tileNum, numTiles));
        }

        Collections.shuffle(tiles);
        this.board = new Board(tiles);
    }

    /**
     * The container for the autosaves, where the undo pulls a game state to bring back.
     */

    public GameStatesContainer movesMade = new GameStatesContainer(3);

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        int counter = 1;

        for (Tile t : board) {
            if (counter != t.getId())
                return false;
            counter++;
        }

        return true;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / SlidingTilesPreNewGameActivity.NUM_COLS;
        int col = position % SlidingTilesPreNewGameActivity.NUM_COLS;
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == SlidingTilesPreNewGameActivity.NUM_ROWS - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == SlidingTilesPreNewGameActivity.NUM_COLS - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / SlidingTilesPreNewGameActivity.NUM_ROWS;
        int col = position % SlidingTilesPreNewGameActivity.NUM_COLS;
        int blankId = board.numTiles();

        int[][] tilesToCheck = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};

        for (int[] pair : tilesToCheck) {
            if (pair[0] >= 0 && pair[0] < SlidingTilesPreNewGameActivity.NUM_ROWS && pair[1] >= 0 && pair[1] < SlidingTilesPreNewGameActivity.NUM_COLS) {
                Tile t = board.getTile(pair[0], pair[1]);
                if (t.getId() == blankId) {
                    board.swapTiles(row, col, pair[0], pair[1]);
                    savesTuple s = new savesTuple(row, col, pair[0], pair[1], SlidingTilesGameActivity.Minutes,
                            SlidingTilesGameActivity.Seconds, SlidingTilesGameActivity.MilliSeconds);
                    this.movesMade.contents.add(s);

                    this.movesMade.currentMoveCounter++;
                    if (this.movesMade.currentMoveCounter - this.movesMade.undoMoveCounter > this.movesMade.getNumUndos()) {
                        this.movesMade.undoMoveCounter++;
                    }
                }
            }
        }
    }


    @Override
    public void undo() {
        savesTuple toTakeFrom = this.movesMade.getPreviousSave();
        int row1 = toTakeFrom.getRow1();
        int row2 = toTakeFrom.getRow2();
        int col1 = toTakeFrom.getCol1();
        int col2 = toTakeFrom.getCol2();
        this.getBoard().swapTiles(row1, col1, row2, col2);
        this.movesMade.currentMoveCounter--;
        this.movesMade.contents.remove(this.movesMade.currentMoveCounter);
        this.movesMade.contents.trimToSize();
    }
}


