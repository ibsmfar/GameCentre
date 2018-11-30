package a207phase1.fall2018.gamecentre;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

/**
 * The dictionary from which words are selected for the game of Hangman
 */
class Hangdiction {
    private Random random = new Random();
    private static final int DEFAULT_WORD_LENGTH = 3;
    private HashSet<String> wordSet;
    private ArrayList<String> wordList;
    //private int wordLength = DEFAULT_WORD_LENGTH;

    /**
     *
     * @param wordListStream the file from which words are being read
     * @throws IOException if there is an IOException
     */
    Hangdiction(InputStream wordListStream) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(wordListStream));
        String line;

        wordSet = new HashSet<>();
        wordList = new ArrayList<>();

        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordSet.add(word);
            wordList.add(word);
        }
    }

    /**
     *
     * @return a word for a game of Hangman
     */
    String pickGoodStarterWord() {
        int index = random.nextInt(wordList.size());
        String t=wordList.get(index);
        if(t.length()<=6)
        return wordList.get(index);
        else return pickGoodStarterWord();
    }
}
