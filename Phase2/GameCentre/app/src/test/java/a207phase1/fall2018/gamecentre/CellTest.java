package a207phase1.fall2018.gamecentre;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class CellTest {
    Cell cell;

    @Before
    public void create(){
        int x = 0;
        int y = 0;
        cell = new Cell(x, y);
    }
    @Test
    public void getXTest() {
        assertSame(0, cell.getX());
    }
    @Test
    public void setXTest() {
        cell.setX(100);
        assertSame(100, cell.getX());
    }
    @Test
    public void getYTest() {
        assertSame(0, cell.getY());
    }
    @Test
    public void setYTest() {
        cell.setY(100);
        assertSame(100, cell.getY());
    }
}