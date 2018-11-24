package a207phase1.fall2018.gamecentre;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertSame;
import java.util.ArrayList;

public class HangmanManagerTest {
    HangmanGameActivity hangmanGameActivity;
    ArrayList<String> lettersGuessed;
    HangmanManager hangmanManager;

    @Before
    public void create(){
        hangmanManager = new HangmanManager("apple",lettersGuessed,
                0, true, "a", 3);
    }

    @Test
    public void getWordToGuessTest() {
        assertSame("apple", hangmanManager.getWordToGuess());
    }
    @Test
    public void setWordToGuessTest() {
        hangmanManager.setWordToGuess("banana");
        assertSame("banana", hangmanManager.getWordToGuess());
    }
    @Test
    public void getLettersGuessedTest() {
        assertSame(lettersGuessed, hangmanManager.getLettersGuessed());
    }
    @Test
    public void setLettersGuessedTest() {
        ArrayList<String> letters = new ArrayList<>();
        hangmanManager.setLettersGuessed(letters);
        assertSame(letters, hangmanManager.getLettersGuessed());
    }
    @Test
    public void getManStateTest() {
        assertSame(0, hangmanManager.getManState());
    }
    @Test
    public void setManStateTest() {
        hangmanManager.setManState(2);
        assertSame(2, hangmanManager.getManState());
    }
    @Test
    public void getDifficultyTest() {
        assertSame(true, hangmanManager.getDifficulty());
    }
    @Test
    public void getWordSoFarTest() {
        assertSame("a", hangmanManager.getWordSoFar());
    }
    @Test
    public void setWordSoFarTest() {
        String word = "app";
        hangmanManager.setWordSoFar(word);
        assertSame("app", hangmanManager.getWordSoFar());
    }


}
