package a207phase1.fall2018.gamecentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import a207phase1.fall2018.gamecentre.User;

public class GameLaunchCentre implements Serializable {

    /**
     * A mapping of usernames to Passwords
     */
    public HashMap<String, String> usernameToPassword = new HashMap<>();
    /**
     * A list of playable games
     */
    private static ArrayList<String> gameList = new ArrayList<>(Arrays.asList("SlidingTiles"));
    /**
     * A list of users
     */
    public ArrayList<User> userList = new ArrayList<>();
    /**
     * The current user playing the game
     */
    public User currentUser;

    /**
     * Adding a user
     */
    public void addUser(User user) {
        userList.add(user);
        usernameToPassword.put(user.getUsername(), user.getPassword());
    }

    /**
     * Checking if username is already taken
     */
    public boolean isUsernameTaken(String username) {
        return usernameToPassword.containsKey(username);
    }

    /**
     * Setting the current user of the game
     */
    public void setUser(String username) {
        for (User u : userList) {
            if (u.getUsername().equals(username))
                currentUser = u;
        }
    }
}



