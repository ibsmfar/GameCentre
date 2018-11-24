package a207phase1.fall2018.gamecentre;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertSame;
import java.util.ArrayList;

public class HangmanGameActivityTest {
    HangmanGameActivity hangmanGameActivity;
    ArrayList<String> lettersGuessed;

    @Before
    public void create(){
        hangmanGameActivity = new HangmanGameActivity();
    }

    @Test
    public void emptyLettersGuessedTest() {
        assertSame("", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
    }

    @Test
    public void nonemptyLettersGuessedTest() {
        assertSame("", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("a");
        assertSame("a", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("o");
        assertSame("a, o", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("w");
        assertSame("a, o, w", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
    }

    @Test
    public void repeatLettersGuessedTest(){
        assertSame("", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("a");
        lettersGuessed.add("a");
        assertSame("a", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("o");
        lettersGuessed.add("a");
        assertSame("a, o", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
    }

    @Test
    public void singleLetterWordCharTest(){
        assertSame('a', hangmanGameActivity.selectChar("a"));
        assertSame('i', hangmanGameActivity.selectChar("i"));
    }
    @Test
    public void multipleLetterWordCharTest(){
        assertSame('p', hangmanGameActivity.selectChar("apple"));
        assertSame('a', hangmanGameActivity.selectChar("banana"));
        assertSame('e', hangmanGameActivity.selectChar("orange"));
    }

}
