package a207phase1.fall2018.gamecentre;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/*
 * The purpose of this class is to provide methods to read and write data
 * to files.
 */
class SavingData {
    final static String USER_LIST = "user_list.ser";
    final static String ST_SCOREBOARD = "sliding_tiles_scoreboard.ser";
    final static String ST_USER_SCOREBOARD = "sliding_tiles_users_scoreboards.ser";
    final static String GAME_SCOREBOARD_2048 = "2048_game_scoreboard.ser";
    final static String HANGMAN_SCOREBOARD_EASY = "hangman_scoreboard_easy.ser";
    final static String HANGMAN_SCOREBOARD_HARD = "hangman_scoreboard_hard.ser";

    static <T> T loadFromFile(String fileName, Context context) {

        try {
            InputStream inputStream = context.openFileInput(fileName);
            if (inputStream != null) {
                ObjectInputStream input = new ObjectInputStream(inputStream);
                T temp = (T) input.readObject();
                inputStream.close();
                return temp;
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
        return null;
    }
    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    static <T> void  saveToFile(String fileName, Context context, T object) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    context.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(object);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

}