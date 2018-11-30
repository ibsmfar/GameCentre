package a207phase1.fall2018.gamecentre;


import java.io.Serializable;
import java.util.ArrayList;

/**
 * A HangmanManager that can be saved and loaded to play a game of Hangman
 */
public class HangmanManager implements Serializable {

    /**
     * The word being guessed
     */
    private String wordToGuess;
    /**
     * The letters that have been guessed so far
     */
    private ArrayList<String> lettersGuessed;
    /**
     * The number of body parts on the man being hanged
     */
    private int manState;
    /**
     * The difficulty of the game being played
     */
    private boolean difficulty;
    /**
     * How much of the wordToGuess that has been guessed so far
     */
    private String wordSoFar;
    /**
     * The number of hints remaining
     */
    private int hints;
    /**
     * The current score in the game
     */
    private int score;
    /**
     * The high score for the session of Hangman
     */
    private int max;

    /**
     * Creates a new HangmanManager
     */
    public HangmanManager(String wordToGuess, ArrayList<String> lettersGuessed,
                          int manState, boolean difficulty, String wordSoFar,
                          int hints, int score,
                          int max){
        this.wordToGuess = wordToGuess;
        this.lettersGuessed = lettersGuessed;
        this.manState = manState;
        this.difficulty = difficulty;
        this.wordSoFar = wordSoFar;
        this.hints = hints;
        this.score = score;
        this.max = max;
    }

    /**
     * a bunch of setters and getters are listed below for the elements in HangmanManager
     *
     */


    public String getWordToGuess(){
        return this.wordToGuess;
    }

    public void setWordToGuess(String wordToGuess){
        this.wordToGuess = wordToGuess;
    }

    public ArrayList<String> getLettersGuessed(){
        return this.lettersGuessed;
    }

    public void setLettersGuessed(ArrayList<String> lettersGuessed){
        this.lettersGuessed = lettersGuessed;}

    public int getManState(){

        return this.manState;
    }

    public void setManState(int manState){

        this.manState = manState;
    }

    public boolean getDifficulty(){

        return this.difficulty;
    }

    public void setWordSoFar(String wordSoFar) {
        this.wordSoFar = wordSoFar;
    }

    public String getWordSoFar() {
        return this.wordSoFar;
    }

    public void setHints(int hints) {
        this.hints = hints;
    }

    public int getHints() {
        return this.hints;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }
}
