package a207phase1.fall2018.gamecentre;

import a207phase1.fall2018.gamecentre.HangmanManager;

import org.junit.Test;

import static org.junit.Assert.*;

import org.junit.Test;
import org.mockito.ArgumentMatchers;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void addition_isCorrect() {

        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetDifficulty() {
        /*ArrayList al = new ArrayList();
        al.add("w");
        new HangmanManager("word", al, 2, false, "word", 1, 4, 5) {
            @Override
            public boolean getDifficulty() {
                return super.getDifficulty();
            }
        };*/


    }

    /*public void testPrintsOneFile() throws IOException {
        Terminal terminal = mock(Terminal.class);
        when(terminal.promptLine()).thenReturn("ls /a");
        Filesystem fs = mock(Filesystem.class);
        when(fs.listDirectoryFiles("/a")).thenReturn(new String[]{"hello"});
        ListCommand ls = new ListCommand(terminal, fs);
        assertTrue(ls.exec(new String[]{"/a"}));
        verify(terminal).printLine(ArgumentMatchers.eq("hello"));
    }*/
}