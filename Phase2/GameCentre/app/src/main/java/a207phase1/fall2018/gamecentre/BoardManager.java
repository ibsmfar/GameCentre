package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private Board board;
    /**
     * Contains the moves that have been made
     */
    private GameStatesContainer movesMade = new GameStatesContainer(3);
    /**
     * Minutes spent solving the board
     */
    private int minutes;
    /**
     * Seconds spent solving the board
     */
    private int seconds;
    /**
     * Milliseconds spent solving the board
     */
    private int milliseconds;



    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
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
     * Return the moves that have been made
     */
    GameStatesContainer getMovesMade(){
        return movesMade;
    }

    /**
     * Return the minutes
     *
     * @return the minutes
     */
    int getMinutes() {
        return minutes;
    }

    /**
     * Return the seconds
     *
     * @return the seconds
     */
    int getSeconds() {
        return seconds;
    }

    /**
     * Return the milliseconds
     *
     * @return the milliseconds
     */
    int getMilliseconds() {
        return milliseconds;
    }

    /**
     * Set the milliseconds
     *
     * @param milliseconds the milliseconds
     */
    void setMilliseconds(int milliseconds) {
        this.milliseconds = milliseconds;
    }

    /**
     * Set the minutes
     *
     * @param minutes the minutes
     */
    void setMinutes(int minutes) {
        this.minutes = minutes;
    }

    /**
     * Set the seconds
     *
     * @param seconds the seconds
     */
    void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    /**
     * Manage a new shuffled board.
     */
    BoardManager(int num_rows, int num_cols) {
        List<Tile> tiles = new ArrayList<>();
        final int numTiles = num_rows * num_cols;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(SlidingTilesPreNewGameActivity.picture, tileNum, numTiles));
        }

        Collections.shuffle(tiles);
        int blankTileRow = blankTileRowLocation(tiles, numTiles);
        if (!isSolvable(num_cols, num_rows, blankTileRow + 1, tiles, numTiles)){
            if (tiles.get(0).getId() == numTiles || tiles.get(1).getId() == numTiles){
                Collections.swap(tiles, numTiles -1, numTiles -2);
            }
            else{
                Collections.swap(tiles, 0, 1);
            }

        }

        this.board = new Board(tiles, num_rows, num_cols);
    }
    int totalInversions(List<Tile> T, int numTiles){
        int numInversions = 0;
        for (int i = 0; i < T.size() - 1; i++){
            for(int j = i + 1; j <T.size(); j++){
                Tile currTile = T.get(i);
                if (currTile.getId() != numTiles){
                    Tile tileToCompare = T.get(j);
                    if (tileToCompare.getId() != numTiles && currTile.getId() > tileToCompare.getId()){
                        numInversions++;
                    }
                }
            }
        }
        return numInversions;
    }
    boolean isSolvable(int num_cols, int num_rows, int blankTileRow, List<Tile> T, int numTiles ){
        if (num_cols % 2 == 1){
            return (totalInversions(T, numTiles) % 2 == 0);
        }
        else{
            return ((totalInversions(T, numTiles) + num_rows - blankTileRow) % 2 == 0);
        }
    }

    /**
     * Location of the row the blank tile is in (array location)
     * @param T the list of tiles
     * @param numtiles the total number of tiles
     * @return the row in which the blank tile is located in
     */
    int blankTileRowLocation(List<Tile> T, int numtiles){
        int location = 0;
        for (int i = 0; i < T.size(); i++){
            Tile currTile = T.get(i);
            if (currTile.getId() == numtiles){
                location = i/(int)Math.sqrt(numtiles);
            }
        }
        return location;
    }

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

        int row = position / board.getNum_rows();
        int col = position % board.getNum_cols();
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getNum_rows() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getNum_cols() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     * if a move has been made, save it in case it needs to be undone
     *
     * @param position the position
     */
    void touchMove(int position) {

        int row = position / board.getNum_rows();
        int col = position % board.getNum_cols();
        int blankId = board.numTiles();

        int[][] tilesToCheck = {{row - 1, col}, {row + 1, col}, {row, col - 1}, {row, col + 1}};

        for (int[] pair : tilesToCheck) {
            if (pair[0] >= 0 && pair[0] < board.getNum_rows() && pair[1] >= 0 && pair[1] < board.getNum_cols()) {
                Tile t = board.getTile(pair[0], pair[1]);
                if (t.getId() == blankId){
                    board.swapTiles(row, col, pair[0], pair[1]);
                    SavesTuple s = new SavesTuple(row, col, pair[0], pair[1]);
                    this.movesMade.contents.add(s);

                    this.movesMade.currentMoveCounter++;
                    if (this.movesMade.currentMoveCounter - this.movesMade.undoMoveCounter > this.movesMade.getNumUndos()) {
                        this.movesMade.undoMoveCounter++;
                    }
                }
            }
        }

    }
    void undo() {
        SavesTuple toTakeFrom = (SavesTuple) this.movesMade.getPreviousSave();
        int row1 = toTakeFrom.getRow1();
        int row2 = toTakeFrom.getRow2();
        int col1 = toTakeFrom.getCol1();
        int col2 = toTakeFrom.getCol2();
        this.getBoard().swapTiles(row1, col1, row2, col2);
        this.movesMade.currentMoveCounter--;
        this.movesMade.contents.remove(this.movesMade.currentMoveCounter);
        this.movesMade.contents.trimToSize();
    }

//    /**
//     * Record a move and update the relevant move and undo counters
//     *
//     * @param save the SavesTuple to be saved
//     */
//    private void addMove(SavesTuple save){
//        this.movesMade.addSavesTuple(save);
//        this.movesMade.updateCounters();
//    }


    @Override
    public String toString(){
        int complexity = this.board.getNum_cols();
        String s = "A " + complexity + " by " + complexity + "board";
        return s;
    }
}
