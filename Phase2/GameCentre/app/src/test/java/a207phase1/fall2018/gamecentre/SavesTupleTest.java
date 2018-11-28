package a207phase1.fall2018.gamecentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class SavesTupleTest {
    SavesTuple savesTuple;

    @Before
    public void create(){
        int row1 = 1;
        int col1 = 2;
        int row2 = 3;
        int col2 = 4;
        savesTuple = new SavesTuple(row1, col1, row2, col2);
    }

    @Test
    public void getRow1Test() { assertSame(1, savesTuple.getRow1());
    }

    @Test
    public void getRow2Test() { assertSame(3, savesTuple.getRow2());
    }

    @Test
    public void getCol1Test() { assertSame(2, savesTuple.getCol1());
    }

    @Test
    public void getCol2Test() { assertSame(4, savesTuple.getCol2());
    }

}