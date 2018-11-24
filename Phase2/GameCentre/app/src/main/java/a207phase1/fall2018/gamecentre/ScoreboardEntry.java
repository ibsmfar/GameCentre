package a207phase1.fall2018.gamecentre;

public class ScoreboardEntry {
    /**
     * An entry to but put into a Scoreboard.
     * AN ENTRY SHOULD BE CREATED WHEN THE GAME IS FINISHED
     * The first element of the Scoreboard list is the name of the user
     * and the next three elements are the Minutes, Seconds, and Milliseconds.
     * This represents the score of the player in this game
     */
    String user;
    int minutes, seconds, milliseconds;
    int complexity; //either 3, 4, or 5

    ScoreboardEntry(User user, int minutes, int seconds, int milliseconds, int complexity){
        this.user = user.getUsername();
        this.minutes = minutes;
        this.seconds = seconds;
        this.milliseconds = milliseconds;
        this.complexity = complexity;

    }


}



