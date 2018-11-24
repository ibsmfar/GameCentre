package a207phase1.fall2018.gamecentre;

import android.app.Activity;

import java.io.Serializable;
import java.util.ArrayList;

import a207phase1.fall2018.gamecentre.HangmanGameActivity;

public class HangmanManager implements Serializable {

    //private MainActivity HangmanBoard;

    private String wordToGuess;
    private ArrayList<String> lettersGuessed;
    private int manState;
    private boolean difficulty;
    private String wordSoFar;
    private int hints;


    public HangmanManager(String wordToGuess, ArrayList<String> lettersGuessed,
                          int manState, boolean difficulty, String wordSoFar, int hints){
        this.wordToGuess = wordToGuess;
        this.lettersGuessed = lettersGuessed;
        this.manState = manState;
        this.difficulty = difficulty;
        this.wordSoFar = wordSoFar;
        this.hints = hints;
    }

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

}
