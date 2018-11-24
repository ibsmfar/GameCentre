package a207phase1.fall2018.gamecentre;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Random;

public class HangmanGameActivity extends AppCompatActivity {

    public ArrayList<String> USED = new ArrayList();
    private boolean REGULAR;
    private Hangdiction dictionary;
    private TextView word;
    int sc=0,max=0;
    public String result = " ";
    public String currentWord;
    private int count=0;
    private HangmanManager game;
    private int hints = 3;

    public static Activity myActivity;
    GameLaunchCentre GLC;
    User currUser;

    Button undoButton;
    Button saveButton;
    Button hintButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(HangmanMenuActivity.TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_hangman_game);
        word = findViewById(R.id.txtWord);

        loadData();
        currUser = GLC.currentUser;
        // undoButton = (Button) findViewById(R.id.btUndo);
        saveButton = findViewById(R.id.btnSaveHangman);
        hintButton = findViewById(R.id.btnHint);

        addSaveButtonListener();

        //addUndoButtonListener();

        REGULAR = HangmanPreNewGameActivity.Regular;
        final EditText editText = findViewById(R.id.txtLetter);

        if (HangmanMenuActivity.loaded == true) {
            game = HangmanMenuActivity.game;
            currentWord = game.getWordToGuess();
            USED = game.getLettersGuessed();
            count = game.getManState();
            REGULAR = game.getDifficulty();
            result = game.getWordSoFar();
            hints = game.getHints();
            sc = game.getScore();
            max = game.getMax();
            createUsedLetterDisplay(USED);
            //word = findViewById(R.id.txtWord);
            TextView usedLetters = findViewById(R.id.txtUsedLetters);
            word.setText(result);
            usedLetters.setText(createUsedLetterDisplay(USED));
            TextView score = findViewById(R.id.score);
            TextView high = findViewById(R.id.high);
            score.setText("" + sc);
            high.setText("" + max);
            //hintButton = findViewById(R.id.btnHint);
            hintButton.setText("Hints: " + hints);

            int r_id = getResources().getIdentifier("hang" + count, "drawable", getApplication().getPackageName());
            ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
            HangmanMenuActivity.loaded = false;
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
                game = new HangmanManager(currentWord, USED, count, REGULAR, result, hints, sc, max);
                autoSave();
            }
        });
        ((Button) findViewById(R.id.btnSolveHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word = (findViewById(R.id.txtWord));
                word.setText(currentWord);
                result = currentWord;
                autoSave();
            }
        });
        ((Button) findViewById(R.id.btnPlayHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
        });
        ((Button) findViewById(R.id.btnHint)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (hints > 0) {
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
                    //result = "";
                    /*for (int i = 0; i < currentWord.length(); i++) {
                        if (currentWord.charAt(i) == hintLetter) {
                            *//*char[] resultArray = result.toCharArray();
                            resultArray[i] = hintLetter;
                            result = String.valueOf(resultArray);*//*
                            result += hintLetter;
                        }
                        else if (wordSoFar.contains(Character.toString(currentWord.charAt(i)))) {
                            result += currentWord.charAt(i);
                        }
                        else {
                            result += "_ ";
                        }
                    }*/
                    for(int i = 0; i < currentWord.length(); ++i) {
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
                    hintButton.setText("Hints: " + hints);
                    word = findViewById(R.id.txtWord);
                    word.setText(result);
                    game.setHints(hints);
                    game.setWordSoFar(result);
                    game.setLettersGuessed(USED);
                    autoSave();
                }
                else {
                    //Toast.makeText(this, "No hints remaining!", Toast.LENGTH_SHORT).show();
                }
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

    public char selectChar(String s) {
        Random randomChar = new Random();
        int indexOfChar = randomChar.nextInt(s.length());
        return s.charAt(indexOfChar);
    }

    private void checkLetter(EditText editText) {
        int i=0,flag=0,k=0;
        TextView word = findViewById(R.id.txtWord);
        TextView usedLetters = findViewById(R.id.txtUsedLetters);
        String letter = editText.getText().toString().trim().toLowerCase();
        if(! USED.contains(letter)) {
            USED.add(letter);
            game.setLettersGuessed(USED);
        }
        for(i=0;i<currentWord.length();++i){
            usedLetters.setText(createUsedLetterDisplay(USED));
            if (currentWord.indexOf(letter,i)!=-1) {
                k = currentWord.indexOf(letter,i);
                result = result.substring(0, 2 * k) + letter + " " + result.substring(2 * k + 2);
                game.setWordSoFar(result);
                word.setText(result);
                i=k;
                flag=1;
                if(result.indexOf("_")==-1) {
                    word.setText("You won !!");
                    USED = new ArrayList();
                    game.setLettersGuessed(USED);
                    usedLetters.setText("");
                    sc+=1;
                    TextView score = findViewById(R.id.score);
                    score.setText(""+sc);
                    if(max<sc){
                        max=sc;
                        TextView high = findViewById(R.id.high);
                        high.setText(""+max);
                    }
                }
            }
        }
        if(flag==0){
            usedLetters.setText(createUsedLetterDisplay(USED));
            if(!REGULAR){
                count=count+2;
            }
            else{
                count=count+1;
            }
            if(count>=6) {
                word.setText(currentWord);
                USED = new ArrayList();
                game.setLettersGuessed(USED);
                count = 6;
                game.setManState(count);
                usedLetters.setText("");
                sc=0;
                TextView score = findViewById(R.id.score);
                score.setText(""+sc);
            }
            game.setManState(count);
            int r_id = getResources().getIdentifier("hang" + count, "drawable", getApplication().getPackageName());
            count = 0;
            ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
        }
        autoSave();
        editText.setText("");
    }

    private void autoSave() {
        game = new HangmanManager(currentWord, USED, count, REGULAR, result, hints, sc, max);
        saveToFile(HangmanMenuActivity.SAVE_FILENAME);
        saveToFile(HangmanMenuActivity.TEMP_SAVE_FILENAME);
    }

    private String createUsedLetterDisplay(ArrayList<String> list){
        String toReturn = "";
        /*if (list.size() == 1) {
            toReturn += list.get(0);
        }*/
            for (int i = 0; i < list.size(); ++i) {
                toReturn += list.get(i) + ", ";
            }
        int len = toReturn.length();
        if (len == 0) {
            return toReturn;
        }
        return toReturn.substring(0,len-2);
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    public void loadFromFile(String fileName) {
        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                game = (HangmanManager) input.readObject();
                currentWord = game.getWordToGuess();
                USED = game.getLettersGuessed();
                count = game.getManState();
                REGULAR = game.getDifficulty();
                result = game.getWordSoFar();
                hints = game.getHints();
                sc = game.getScore();
                max = game.getMax();
                createUsedLetterDisplay(USED);
                //word = findViewById(R.id.txtWord);
                word.setText(result);
                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(game);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    private void loadData() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences.getString("GameLaunchCentre", null);
        Type type = new TypeToken<GameLaunchCentre>() {
        }.getType();
        GLC = gson.fromJson(json, type);

        if (GLC == null) {
            GLC = new GameLaunchCentre();
        }
    }

    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.btnSaveHangman);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile(HangmanMenuActivity.SAVE_FILENAME);
                saveToFile(HangmanMenuActivity.TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }
    
    private void makeToastSavedText() {
        Toast.makeText(this, "Successfully Saved!", Toast.LENGTH_SHORT).show();
    }

//    private void addUndoButtonListener() {
//        undoButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (boardManager.movesMade.currentMoveCounter == boardManager.movesMade.undoMoveCounter) {
//                    makeToastOutOfUndos();
//                }
//                else{
//                    boardManager.undo();
//                }
//            }
//        });
//    }
}
