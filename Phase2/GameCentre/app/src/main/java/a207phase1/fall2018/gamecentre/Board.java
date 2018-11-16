package a207phase1.fall2018.gamecentre;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The number of rows.
     */
    public static int NUM_ROWS; // changed from final static
    public int num_rows;

    /**
     * The number of rows.
     */
    public static int NUM_COLS; //changed from final static
    public int num_cols;

    public static int picture;

    /**
     * The tiles on the board in row-major order.
     */
    // public static Tile[][] tiles = new Tile[SlidingTilesPreNewGameActivity.NUM_ROWS][SlidingTilesPreNewGameActivity.NUM_COLS];
    public Tile[][] tiles = new Tile[num_rows][num_cols];

    //public static Tile[][] tiles;
    /**
     * Return an iterator over the tiles in board.
     *
     * @return an iterator
     */
    @Override
    @NonNull
    public Iterator<Tile> iterator() {
        return new MyIterator();
    }

    /**
     * Implementation of the iterator for the board class
     */
    private class MyIterator implements Iterator<Tile> {
        int rowIndex = 0;
        int columnIndex = 0;

        /**
         * The next tile in the board
         *
         * @return the next tile
         */
        @Override
        public Tile next() {
            Tile result = tiles[rowIndex][columnIndex];
            if (columnIndex == SlidingTilesPreNewGameActivity.NUM_COLS - 1) {
                columnIndex = 0;
                rowIndex++;

            } else {
                columnIndex++;
            }
            return result;
        }

        /**
         * Whether or not there is one more tile to check
         *
         * @return true if there is a next tile, false otherwise
         */
        @Override
        public boolean hasNext() {
            return (columnIndex < SlidingTilesPreNewGameActivity.NUM_COLS && rowIndex < SlidingTilesPreNewGameActivity.NUM_ROWS);
        }
    }

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == SlidingTilesPreNewGameActivity.NUM_ROWS * SlidingTilesPreNewGameActivity.NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles) {
        picture = SlidingTilesPreNewGameActivity.picture;
        this.tiles = new Tile[SlidingTilesPreNewGameActivity.NUM_ROWS][SlidingTilesPreNewGameActivity.NUM_COLS];
        //this.tiles = new Tile[num_rows][num_cols];
        Iterator<Tile> iter = tiles.iterator();

        for (int row = 0; row != SlidingTilesPreNewGameActivity.NUM_ROWS; row++) {
            for (int col = 0; col != SlidingTilesPreNewGameActivity.NUM_COLS; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return SlidingTilesPreNewGameActivity.NUM_COLS * SlidingTilesPreNewGameActivity.NUM_ROWS;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile temp1 = this.tiles[row1][col1];
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp1;

        setChanged();
        notifyObservers();
    }

    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }
}
