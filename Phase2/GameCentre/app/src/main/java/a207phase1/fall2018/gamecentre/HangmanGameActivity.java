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

public class HangmanGameActivity extends AppCompatActivity {
    public ArrayList<String> USED = new ArrayList();
    private boolean REGULAR = true;
    private com.example.android.hang.Hangdiction dictionary;
    private TextView word;
    int sc=0,max=0;
    public String result = " ";
    public String currentWord;
    private int count=0;
    private com.example.android.hangman.HangmanManager game;

    public static Activity myActivity;
    GameLaunchCentre GLC;
    User currUser;

    Button undoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(SlidingTilesMenuActivity.TEMP_SAVE_FILENAME);
        setContentView(R.layout.activity_hangman_game);

        loadData();
        currUser = GLC.currentUser;
        // undoButton = (Button) findViewById(R.id.btUndo);

        //addUndoButtonListener();

        final EditText editText = findViewById(R.id.letter);
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
                word = (findViewById(R.id.word));
                result = "";
                word.setText("");
                for (int i = 0; i < currentWord.length(); i += 1) {
                    result += "_ ";
                }
                word.setText(result);
                EditText editText = findViewById(R.id.letter);
                editText.setEnabled(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);

            }
        });
        ((Button) findViewById(R.id.btnSolveHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                word = (findViewById(R.id.word));
                word.setText(currentWord);
            }
        });
        ((Button) findViewById(R.id.btnPlayHangman)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentWord == null) {
                    currentWord = dictionary.pickGoodStarterWord();
                    word = (findViewById(R.id.word));
                    for (int i = 0; i < currentWord.length(); i += 1) {
                        result += "_ ";
                    }
                    word.setText(result);
                }
                EditText editText = findViewById(R.id.letter);
                editText.setEnabled(true);
                editText.requestFocus();
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new com.example.android.hang.Hangdiction(inputStream);
        } catch (IOException e) {
            Toast toast = Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG);
            toast.show();
        }
        game = new com.example.android.hangman.HangmanManager(currentWord, USED, count, REGULAR);
    }

    private void checkLetter(EditText editText) {
        int i=0,flag=0,k=0;
        TextView word = findViewById(R.id.word);
        TextView usedLetters = findViewById(R.id.usedLetters);
        String letter = editText.getText().toString().trim().toLowerCase();
        USED.add(letter);
        game.setGuesses(USED);
        for(i=0;i<currentWord.length();++i){
            usedLetters.setText(createUsedLetterDisplay(USED));
            if (currentWord.indexOf(letter,i)!=-1) {
                k = currentWord.indexOf(letter,i);
                result = result.substring(0, 2 * k) + letter + " " + result.substring(2 * k + 2);
                word.setText(result);
                i=k;
                flag=1;
                if(result.indexOf("_")==-1) {
                    word.setText("You won !!");
                    USED = new ArrayList();
                    game.setGuesses(USED);
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
            if(!REGULAR){ count=count+2; }
            else{ count=count+1; }
            if(count>=6) {
                word.setText(currentWord);
                USED = new ArrayList();
                game.setGuesses(USED);
                game.setMan(count);
                usedLetters.setText("");
                sc=0;
                TextView score = findViewById(R.id.score);
                score.setText(""+sc);
            }
            int r_id = getResources().getIdentifier("hang" + count, "drawable", getApplication().getPackageName());
            ((ImageView) findViewById(R.id.hang)).setImageDrawable(getDrawable(r_id));
        }
        editText.setText("");
    }

    private String createUsedLetterDisplay(ArrayList<String> list){
        String toReturn = "";
        for(int i=0; i<list.size(); ++i){
            toReturn += list.get(i) + ", ";
        }
        int len = toReturn.length();
        return toReturn.substring(0,len-1);
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
