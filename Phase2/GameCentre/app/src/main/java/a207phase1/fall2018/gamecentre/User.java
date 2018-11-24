package a207phase1.fall2018.gamecentre;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Class representation of a User.
 */
public class User implements Serializable{
    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * ArrayList which stores all the games containing all their boards which
     * the user has saved.
     */
    public ArrayList<GameStatesContainer> games = new ArrayList<>();

    /**
     * Constructor for User class.
     */
    public User(String username, String password) {
        setUsername(username);
        setPassword(password);
    }
    /**
     * Setter for the user's username.
     */
    public void setUsername(String username){
        this.username = username;
    }
    /**
     * Getter for the user's username.
     */
    public String getUsername(){
        return this.username;
    }
    /**
     * Setter for the user's password.
     */
    public void setPassword(String password){
        this.password = password;
    }
    /**
     * Getter for the user's password.
     */
    public String getPassword(){
        return this.password;
    }
    /**
     * String representation of the user.
     */
    public String toString(){ return this.username + this.password; }
}
