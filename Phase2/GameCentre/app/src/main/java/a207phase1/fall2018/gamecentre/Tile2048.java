package a207phase1.fall2018.gamecentre;

public class Tile2048 extends Cell {
    private final int value;
    private Tile2048[] mergedFrom = null;

    public Tile2048(int x, int y, int value) {
        super(x, y);
        this.value = value;
    }

    public Tile2048(Cell cell, int value) {
        super(cell.getX(), cell.getY());
        this.value = value;
    }

    public void updatePosition(Cell cell) {
        this.setX(cell.getX());
        this.setY(cell.getY());
    }

    public int getValue() {
        return this.value;
    }

    public Tile2048[] getMergedFrom() {
        return mergedFrom;
    }

    public void setMergedFrom(Tile2048[] tile) {
        mergedFrom = tile;
    }
}
