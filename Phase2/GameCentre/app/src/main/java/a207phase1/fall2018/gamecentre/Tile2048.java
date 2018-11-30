package a207phase1.fall2018.gamecentre;

/**
 * A 2048 Tile
 */
public class Tile2048 extends Cell {
    private final int value;
    private Tile2048[] mergedFrom = null;

    Tile2048(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    Tile2048(Cell cell, int value) {
        super(cell.getX(), cell.getY());
        this.value = value;
    }

    /**
     * updates the tile's location to that of cell
     * @param cell of the new coordinates
     */
    void updatePosition(Cell cell) {
        this.setX(cell.getX());
        this.setY(cell.getY());
    }

    int getValue() {
        return this.value;
    }

    Tile2048[] getMergedFrom() {
        return mergedFrom;
    }

    void setMergedFrom(Tile2048[] tile) {
        mergedFrom = tile;
    }
}
