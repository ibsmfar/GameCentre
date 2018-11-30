package a207phase1.fall2018.gamecentre;

import java.io.Serializable;

public class UserHangmanManager implements Serializable {
    HangmanManager easy;
    HangmanManager hard;
    int easyHighScore = 0;
    int hardHighScore = 0;
}
