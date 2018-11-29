package a207phase1.fall2018.gamecentre;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertSame;

public class UserTest {
    User user;

    @Before
    public void create(){
        String username = "username";
        String password = "password";
        user = new User(username, password);
    }

    @Test
    public void getPasswordTest() {
        assertSame("password", user.getPassword());
    }

    @Test
    public void getUsernameTest() {
        assertSame("username", user.getUsername());
    }

}
