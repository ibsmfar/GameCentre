package a207phase1.fall2018.gamecentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class TileTest {
    Tile tile;

    @Before
    public void create(){
        int id = 0;
        int background = 0;
        tile = new Tile(id, background);
    }

    @Test
    public void getIdTest() {
        assertSame(0, tile.getId());
    }
    @Test
    public void getBackgroundTest() {
        assertSame(0, tile.getBackground());
    }
    @Test
    public void uoftTile3x3Test() {
        tile.uoftTile3x3(0);
        assertSame(1, tile.getId());
    }
    @Test
    public void uoftTile4x4Test() {
        tile.uoftTile4x4(0);
        assertSame(1, tile.getId());
    }

    @Test
    public void uoftTile5x5Test() {
        tile.uoftTile5x5(0);
        assertSame(1, tile.getId());
    }
    @Test
    public void penguinTile3X3Test() {
        tile.penguin3x3(0);
        assertSame(1, tile.getId());
    }
    @Test
    public void penguinTile4X4Test() {
        tile.penguin4x4(0);
        assertSame(1, tile.getId());
    }
    @Test
    public void penguinTile5X5Test() {
        tile.penguin4x4(0);
        assertSame(1, tile.getId());
    }
    @Test
    public void numberTileTestZero() {
        tile.numberTile(0,16);
        assertSame(1, tile.getId());
    }
    @Test
    public void numberTileTestNonZero() {
        tile.numberTile(10,16);
        assertSame(11, tile.getId());
    }
}

