package a207phase1.fall2018.gamecentre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * The Hangman game activity.
 */
public class HangmanGameActivity extends AppCompatActivity {

    /**
     * Used letters
     */
    public ArrayList<String> USED = new ArrayList();

    /**
     * Difficulty level
     */
    private boolean regular;

    private Hangdiction dictionary;

    /**
     * Letter that is guessed
     */
    private TextView word;

    /**
     * Current score and max score
     */
    int sc = 0, max = 0;

    /**
     * Word guessed so far
     */
    public String result = " ";

    /**
     * Word to be guessed
     */
    public String currentWord;

    /**
     * The Hangman state
     */
    private int count = 0;


    private HangmanManager game;

    /**
     * Number of hints remaining
     */
    private int hints = 3;

    public static Activity myActivity;
    String username;
    boolean newGame;
    ArrayList<User> listOfUsers;
    User currUser;

    Button saveButton;
    Button hintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_game);

        Intent gameSelection = getIntent();
        Bundle userBundle = gameSelection.getExtras();

        if (userBundle != null) {
            username = userBundle.getString("Username");
            newGame = userBundle.getBoolean("NewGame");
            regular = userBundle.getBoolean("Difficulty", true);

        }

        listOfUsers = SavingData.loadFromFile(SavingData.USER_LIST, this);
        setUser();

        if (regular){
            max = currUser.userHangmanManager.regularUserHighScore;
            TextView high = findViewById(R.id.high);
            high.setText(""+max);
        }
        else{
            max = currUser.userHangmanManager.difficultUserHighScore;
            TextView high = findViewById(R.id.high);
            high.setText(""+max);
        }

        word = findViewById(R.id.txtWord);

        saveButton = findViewById(R.id.btnSaveHangman);
        hintButton = findViewById(R.id.btnHint);

        addSaveButtonListener(this);

        final EditText editText = findViewById(R.id.txtLetter);

        if (!newGame) {
            loadHangmanManager();
        }

        editText.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editText.setImeOptions(EditorInfo.IME_ACTION_GO);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    checkLetter(editText);
                }
                return true;
            }
        });

        (findViewById(R.id.btnResetHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                int r_id = getResources().getIdentifier("hang0", "drawable", getApplication().getPackageName());
                ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
                currentWord = null;
                currentWord = dictionary.pickGoodStarterWord();
                word = (findViewById(R.id.txtWord));
                result = "";
                word.setText("");
                for (int i = 0; i < currentWord.length(); i += 1) {
                    result += "_ ";
                }
                word.setText(result);
                EditText editText = findViewById(R.id.txtLetter);
                USED = new ArrayList();
                TextView usedLetters = findViewById(R.id.txtUsedLetters);
                usedLetters.setText("");
                editText.setEnabled(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                if (hints > 0){
                    hintButton.setEnabled(true);
                }
                game = new HangmanManager(currentWord, USED, count, regular, result, hints, sc, max);
                autoSave();
            }
        });
        (findViewById(R.id.btnSolveHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word = (findViewById(R.id.txtWord));
                word.setText(currentWord);
                result = currentWord;
                findViewById(R.id.btnHint).setEnabled(false);
                autoSave();
            }
        });
        (findViewById(R.id.btnPlayHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordToCompare = result.replaceAll("\\s+","");
                if (currentWord == null || !currentWord.equals(wordToCompare)) {
                    hintButton.setEnabled(true);
                    if (currentWord == null) {
                        currentWord = dictionary.pickGoodStarterWord();
                        word = (findViewById(R.id.txtWord));
                        for (int i = 0; i < currentWord.length(); i += 1) {
                            result += "_ ";
                        }
                        word.setText(result);
                    }
                    EditText editText = findViewById(R.id.txtLetter);
                    editText.setEnabled(true);
                    editText.requestFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
                    autoSave();
                }
                else {
                    makeResetToast();
                }
            }
        });
        (findViewById(R.id.btnHint)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int k = 0;
                String wordToSend = "";
                String wordSoFar;
                wordSoFar = word.getText().toString();
                for (int j = 0; j < currentWord.length(); j++) {
                    if (!wordSoFar.contains(Character.toString(currentWord.charAt(j)))) {
                        wordToSend += currentWord.charAt(j);
                    }
                }
                char hintLetter = selectChar(wordToSend);
                for (int i = 0; i < currentWord.length(); ++i) {
                    if (currentWord.indexOf(hintLetter, i) != -1) {
                        k = currentWord.indexOf(hintLetter, i);
                        result = result.substring(0, 2 * k) + hintLetter + " " + result.substring(2 * k + 2);
                        game.setWordSoFar(result);
                        word.setText(result);
                    }
                }
                TextView usedLetters = findViewById(R.id.txtUsedLetters);
                USED.add(Character.toString(hintLetter));
                usedLetters.setText(createUsedLetterDisplay(USED));
                hints -= 1;
                if (hints == 0) {
                    hintButton.setEnabled(false);
                }
                hintButton.setText("Hints: " + hints);
                word = findViewById(R.id.txtWord);
                word.setText(result);
                game.setHints(hints);
                game.setWordSoFar(result);
                game.setLettersGuessed(USED);
                autoSave();
            }
        });
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new Hangdiction(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        switchToMenu();
    }
    private void switchToMenu(){
        Intent tmp = new Intent(this, HangmanMenuActivity.class);
        tmp.putExtra("Username", username);
        startActivity(tmp);
    }

    /**
     * Used to load data of previous Hangman game
     */
    private void loadHangmanManager(){
        if (regular){
            game = currUser.userHangmanManager.regularHangmanManager;
            max = currUser.userHangmanManager.regularUserHighScore;
        }
        else{
            game = currUser.userHangmanManager.difficultHangmanManager;
            max = currUser.userHangmanManager.difficultUserHighScore;
        }
        currentWord = game.getWordToGuess();
        USED = game.getLettersGuessed();
        count = game.getManState();
        regular = game.getDifficulty();
        result = game.getWordSoFar();
        hints = game.getHints();
        sc = game.getScore();
        createUsedLetterDisplay(USED);
        TextView usedLetters = findViewById(R.id.txtUsedLetters);
        word.setText(result);
        usedLetters.setText(createUsedLetterDisplay(USED));
        TextView score = findViewById(R.id.score);
        TextView high = findViewById(R.id.high);
        score.setText("" + sc);
        high.setText("" + max);
        hintButton.setText("Hints: " + hints);

        if (hints == 0) {
            hintButton.setEnabled(false);
        }

        int r_id = getResources().getIdentifier("hang" + count, "drawable", getApplication().getPackageName());
        ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
    }

    private void addSaveButtonListener(final Context context) {
        Button saveButton = findViewById(R.id.btnSaveHangman);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateManager(regular);
                SavingData.saveToFile(SavingData.USER_LIST, context, listOfUsers);
                makeToastSavedText();
            }
        });
    }

    public char selectChar(String s) {
        Random randomChar = new Random();
        int indexOfChar = randomChar.nextInt(s.length());
        return s.charAt(indexOfChar);
    }

    void setUser() {
        for (User u : listOfUsers) {
            if (u.getUsername().equals(username)) {
                currUser = u;
            }
        }
    }

    void updateManager(boolean regular){
        for (User u: listOfUsers){
            if (u.getUsername().equals(username)){
                if(regular){
                    u.userHangmanManager.regularHangmanManager = game;
                    u.userHangmanManager.regularUserHighScore = max;
                }
                else{
                    u.userHangmanManager.difficultHangmanManager = game;
                    u.userHangmanManager.difficultUserHighScore = max;
                }

            }
        }
    }

    private void checkLetter(EditText editText) {
        int i = 0, flag = 0, k = 0;
        TextView word = findViewById(R.id.txtWord);
        TextView usedLetters = findViewById(R.id.txtUsedLetters);
        String letter = editText.getText().toString().trim().toLowerCase();
        if (letter.length() != 1) {
            makeEnterALetterToast();
        }
        for (i = 0; i < currentWord.length(); ++i) {
            //usedLetters.setText(createUsedLetterDisplay(USED));
            if (currentWord.indexOf(letter, i) != -1) {
                if (!USED.contains(letter)) {
                    k = currentWord.indexOf(letter, i);
                    result = result.substring(0, 2 * k) + letter + " " + result.substring(2 * k + 2);
                    game.setWordSoFar(result);
                    word.setText(result);
                    i = k;
                    flag = 1;
                }
                else {
                    flag = 1;
                }
            }
        }
        if (!USED.contains(letter)) {
            USED.add(letter);
            game.setLettersGuessed(USED);
        }
        usedLetters.setText(createUsedLetterDisplay(USED));
        if (result.indexOf("_") == -1) {
            word.setText("You won !!");
            USED = new ArrayList();
            game.setLettersGuessed(USED);
            usedLetters.setText("");
            sc += 1;
            TextView score = findViewById(R.id.score);
            score.setText("" + sc);
            if (max < sc) {
                max = sc;
                TextView high = findViewById(R.id.high);
                high.setText("" + max);
            }
        }
        if (flag == 0) {
            usedLetters.setText(createUsedLetterDisplay(USED));
            if (!regular) {
                count = count + 2;
            } else {
                count = count + 1;
            }
            if (count >= 6) {
                word.setText(currentWord);
                result = word.getText().toString();
                USED = new ArrayList();
                game.setLettersGuessed(USED);
                count = 6;
                game.setManState(count);
                usedLetters.setText("");
                checkAndAddScoreboardEntry();
                hintButton.setText("Hints: " + hints);
                sc=0;
                TextView score = findViewById(R.id.score);
                score.setText("" + sc);
            }
            game.setManState(count);
            int r_id = getResources().getIdentifier("hang" + count, "drawable", getApplication().getPackageName());
            if (count > 6) {
                count = 0;
                ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
            }
            else {
                ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
            }
        }
        autoSave();
        editText.setText("");
    }

    private void autoSave() {
        game = new HangmanManager(currentWord, USED, count, regular, result, hints, sc, max);
        updateManager(regular);
        SavingData.saveToFile(SavingData.USER_LIST, this, listOfUsers);
    }
    void checkAndAddScoreboardEntry(){
        if(regular){
            ArrayList<HangmanScoreboardEntry> gameScores = SavingData.loadFromFile(
                    SavingData.HANGMAN_SCOREBOARD_EASY, this);
            HangmanScoreboardEntry newEntry = new HangmanScoreboardEntry(username, sc);
            gameScores.add(newEntry);
            Collections.sort(gameScores, new SortByHangmanScore());
            if (gameScores.size() == 4){
                gameScores.remove(3);
            }
            SavingData.saveToFile(SavingData.HANGMAN_SCOREBOARD_EASY, this, gameScores);
        }
        else{
            ArrayList<HangmanScoreboardEntry> gameScores = SavingData.loadFromFile(
                    SavingData.HANGMAN_SCOREBOARD_HARD, this);
            HangmanScoreboardEntry newEntry = new HangmanScoreboardEntry(username, sc);
            gameScores.add(newEntry);
            Collections.sort(gameScores, new SortByHangmanScore());
            if (gameScores.size() == 4){
                gameScores.remove(3);
            }
            SavingData.saveToFile(SavingData.HANGMAN_SCOREBOARD_HARD, this, gameScores);


        }
    }

    public String createUsedLetterDisplay(ArrayList <String> list) {
        String toReturn = "";
        for (int i = 0; i < list.size(); ++i) {
            toReturn += list.get(i) + ", ";
        }
        int len = toReturn.length();
        if (len == 0) {
            return toReturn;
        }
        return toReturn.substring(0, len - 2);
    }


    private void makeToastSavedText() {
        Toast.makeText(this, "Successfully Saved!", Toast.LENGTH_SHORT).show();
    }
    private void makeEnterALetterToast() {
        Toast.makeText(this, "Enter one letter only!", Toast.LENGTH_SHORT).show();
    }
    private void makeResetToast() {
        Toast.makeText(this, "Press Reset!", Toast.LENGTH_SHORT).show();
    }
}

