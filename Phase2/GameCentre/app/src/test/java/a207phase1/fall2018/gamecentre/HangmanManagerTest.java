package a207phase1.fall2018.gamecentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;

public class HangmanManagerTest {
    ArrayList<String> lettersGuessed;
    HangmanManager hangmanManager;

    @Before
    public void create(){
        hangmanManager = new HangmanManager("apple", lettersGuessed,
                0, true, "a", 3, 4, 4);
    }

    @Test
    public void getWordToGuessTest() {
        assertEquals("apple", hangmanManager.getWordToGuess());
    }
    @Test
    public void setWordToGuessTest() {
        hangmanManager.setWordToGuess("banana");
        assertEquals("banana", hangmanManager.getWordToGuess());
    }
    @Test
    public void getLettersGuessedTest() {
        assertEquals(lettersGuessed, hangmanManager.getLettersGuessed());
    }
    @Test
    public void setLettersGuessedTest() {
        ArrayList<String> letters = new ArrayList<>();
        hangmanManager.setLettersGuessed(letters);
        assertEquals(letters, hangmanManager.getLettersGuessed());
    }
    @Test
    public void getManStateTest() {

        assertEquals(0, hangmanManager.getManState());
    }

    @Test
    public void setManStateTest() {
        hangmanManager.setManState(2);
        assertEquals(2, hangmanManager.getManState());
    }

    @Test
    public void getDifficultyTest() {
        assertEquals(true, hangmanManager.getDifficulty());
    }

    @Test
    public void getWordSoFarTest() {
        assertEquals("a", hangmanManager.getWordSoFar());
    }

    @Test
    public void setWordSoFarTest() {
        String word = "app";
        hangmanManager.setWordSoFar(word);
        assertEquals("app", hangmanManager.getWordSoFar());
    }

    @Test
    public void getHints() {
        assertEquals(3, hangmanManager.getHints());
    }

    @Test
    public void setHints() {
        int hints = 1;
        hangmanManager.setHints(hints);
        assertEquals(1, hangmanManager.getHints());
    }

    @Test
    public void getScore() {
        assertEquals(4, hangmanManager.getScore());
    }

    @Test
    public void setScore() {
        int score = 10;
        hangmanManager.setScore(score);
        assertEquals(10, hangmanManager.getScore());
    }

    @Test
    public void getMax() {
        assertEquals(4, hangmanManager.getMax());
    }

    @Test
    public void setMax() {
        int score = 10;
        hangmanManager.setMax(score);
        assertEquals(10, hangmanManager.getMax());
    }

}
