package a207phase1.fall2018.gamecentre;

import java.util.ArrayList;

/**
 * A grid for managing the tiles in a 2048 game
 */
class Grid2048 {
    /**
     * The 2d array of tiles
     */
    final Tile2048[][] field;
    /**
     * The 2d array of tiles that stores the tiles to be undone
     */
    final Tile2048[][] undoField;
    /**
     * A temporary 2d array of tiles that helps with undoing and updating tiles
     */
    private final Tile2048[][] bufferField;

    /**
     * creates a Grid2048 object
     * @param sizeX The width of the grid
     * @param sizeY The height of the grid
     */
    Grid2048(int sizeX, int sizeY) {
        field = new Tile2048[sizeX][sizeY];
        undoField = new Tile2048[sizeX][sizeY];
        bufferField = new Tile2048[sizeX][sizeY];
        clearGrid();
        clearUndoGrid();
    }

    /**
     * Randomly returns a cell from the list of available cells
     * @return a cell that is not currently occupied
     */
    Cell randomAvailableCell() {
        ArrayList<Cell> availableCells = getAvailableCells();
        if (availableCells.size() >= 1) {
            return availableCells.get((int) Math.floor(Math.random() * availableCells.size()));
        }
        return null;
    }

    /**
     * Returns the cells that are available
     * @return an ArrayList of cells that are available
     */
    private ArrayList<Cell> getAvailableCells() {
        ArrayList<Cell> availableCells = new ArrayList<>();
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    availableCells.add(new Cell(xx, yy));
                }
            }
        }
        return availableCells;
    }

    /**
     * Checks true if there is at least one unoccupied cell
     * @return whether the number of available cells is greater than 0
     */
    boolean isCellsAvailable() {
        return (getAvailableCells().size() >= 1);
    }

    /**
     * Check if the cell is available
     * @param cell The cell in question
     * @return if the cell is empty or not
     */
    boolean isCellAvailable(Cell cell) {
        return !isCellOccupied(cell);
    }

    /**
     * checks if the cell is empty
     * @param cell the cell in question
     * @return if the cell is null or not
     */
    boolean isCellOccupied(Cell cell) {
        return (getCellContent(cell) != null);
    }

    /**
     * returns the content of the cell in question
     * @param cell the cell in question
     * @return the tile at the location of the cell
     */
    Tile2048 getCellContent(Cell cell) {
        if (cell != null && isCellWithinBounds(cell)) {
            return field[cell.getX()][cell.getY()];
        } else {
            return null;
        }
    }

    /**
     * returns the content of the cell in question
     * @param x x value of the cell
     * @param y y value of the cell
     * @return the tile at the location of the cell
     */
    Tile2048 getCellContent(int x, int y) {
        if (isCellWithinBounds(x, y)) {
            return field[x][y];
        } else {
            return null;
        }
    }

    /**
     *
     * @param cell the cell in question
     * @return if the cell is within the bounds of the game
     */
    boolean isCellWithinBounds(Cell cell) {
        return 0 <= cell.getX() && cell.getX() < field.length
                && 0 <= cell.getY() && cell.getY() < field[0].length;
    }

    /**
     *
     * @param x the x value of the cell
     * @param y the y value of the cell
     * @return if the cell at (x,y) is within the bounds of the game
     */
    boolean isCellWithinBounds(int x, int y) {
        return 0 <= x && x < field.length
                && 0 <= y && y < field[0].length;
    }

    /**
     * Inserts a tile into field based on its coordinates
     * @param tile the tile to be inserted
     */
    void insertTile(Tile2048 tile) {
        field[tile.getX()][tile.getY()] = tile;
    }

    /**
     * Removes the tile from field
     * @param tile the tile to be removed
     */
    void removeTile(Tile2048 tile) {
        field[tile.getX()][tile.getY()] = null;
    }

    /**
     * Saves the tiles in bufferField into undoField
     */
    void saveTiles() {
        for (int xx = 0; xx < bufferField.length; xx++) {
            for (int yy = 0; yy < bufferField[0].length; yy++) {
                if (bufferField[xx][yy] == null) {
                    undoField[xx][yy] = null;
                } else {
                    undoField[xx][yy] = new Tile2048(xx, yy, bufferField[xx][yy].getValue());
                }
            }
        }
    }

    /**
     * Saves the tiles in field into bufferField
     */
    void prepareSaveTiles() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                if (field[xx][yy] == null) {
                    bufferField[xx][yy] = null;
                } else {
                    bufferField[xx][yy] = new Tile2048(xx, yy, field[xx][yy].getValue());
                }
            }
        }
    }

    /**
     * change the tiles in field back into the ones in undoField
     */
    void revertTiles() {
        for (int xx = 0; xx < undoField.length; xx++) {
            for (int yy = 0; yy < undoField[0].length; yy++) {
                if (undoField[xx][yy] == null) {
                    field[xx][yy] = null;
                } else {
                    field[xx][yy] = new Tile2048(xx, yy, undoField[xx][yy].getValue());
                }
            }
        }
    }

    /**
     * Completely resets field
     */
    void clearGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                field[xx][yy] = null;
            }
        }
    }

    /**
     * Completely rests undoField
     */
    void clearUndoGrid() {
        for (int xx = 0; xx < field.length; xx++) {
            for (int yy = 0; yy < field[0].length; yy++) {
                undoField[xx][yy] = null;
            }
        }
    }
}
