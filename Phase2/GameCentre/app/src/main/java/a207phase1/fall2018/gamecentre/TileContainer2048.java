package a207phase1.fall2018.gamecentre;

import java.io.Serializable;

public class TileContainer2048 implements Serializable {

    private int xPosition;
    private int yPosition;
    private int value;

    public TileContainer2048(int xPosition, int yPosition, int value){
        this.value = value;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public int getValue() {
        return value;
    }
}
