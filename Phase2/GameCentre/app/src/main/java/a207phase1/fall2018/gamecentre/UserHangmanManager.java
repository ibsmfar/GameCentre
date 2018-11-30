package a207phase1.fall2018.gamecentre;

import java.io.Serializable;

/**
 * Used for storing a user's hangman manager games
 */
public class UserHangmanManager implements Serializable {
    HangmanManager regularHangmanManager;
    HangmanManager difficultHangmanManager;
    int regularUserHighScore = 0;
    int difficultUserHighScore = 0;
}
