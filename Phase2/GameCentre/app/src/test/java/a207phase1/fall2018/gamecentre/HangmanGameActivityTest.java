package a207phase1.fall2018.gamecentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import java.util.ArrayList;


public class HangmanGameActivityTest {
    HangmanGameActivity hangmanGameActivity;
    ArrayList<String> lettersGuessed = new ArrayList<>();

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
        assertEquals("", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("a");
        assertEquals("a", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("o");
        assertEquals("a, o", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("w");
        assertEquals("a, o, w", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
    }

    @Test
    public void repeatLettersGuessedTest(){
        assertEquals("", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("a");
        lettersGuessed.add("a");
        assertEquals("a, a", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
        lettersGuessed.add("o");
        lettersGuessed.add("a");
        assertEquals("a, a, o, a", hangmanGameActivity.createUsedLetterDisplay(lettersGuessed));
    }

    @Test
    public void singleLetterWordCharTest(){
        assertEquals('a', hangmanGameActivity.selectChar("a"));
        assertEquals('i', hangmanGameActivity.selectChar("i"));
    }

    @Test
    public void multipleLetterWordCharTest(){
        assertEquals('p', hangmanGameActivity.selectChar("apple"));
        assertEquals('a', hangmanGameActivity.selectChar("banana"));
        assertEquals('e', hangmanGameActivity.selectChar("orange"));
    }

}
