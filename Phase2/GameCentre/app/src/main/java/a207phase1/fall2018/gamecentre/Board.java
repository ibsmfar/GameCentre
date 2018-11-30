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
    private int num_rows;

    /**
     * The number of rows.
     */
    private int num_cols;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    Board(List<Tile> tiles, int num_rows, int num_cols) {
        Iterator<Tile> iter = tiles.iterator();
        this.num_rows = num_rows;
        this.num_cols = num_cols;
        this.tiles =  new Tile[num_cols][num_rows];


        for (int row = 0; row != this.num_rows; row++) {
            for (int col = 0; col != num_cols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }
    /**
     * Return the number of rows on the board
     *
     * @return the number of rows on the board
     */
    int getNum_rows(){
        return this.num_rows;
    }
    /**
     * Return the number of columns on the board
     *
     * @return the number of columns on the board
     */
    int getNum_cols(){
        return this.num_cols;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return num_cols * num_rows;
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
            if (columnIndex == num_cols - 1) {
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
            return (columnIndex < num_cols && rowIndex < num_rows);
        }
    }
}
