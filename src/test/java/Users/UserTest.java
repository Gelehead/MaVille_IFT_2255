package Users;

import org.junit.jupiter.api.Test;

import Instances.User;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    @Test
    public void testUserCreation() {
        User user = new User("John", "Doe", "john.doe@example.com", "password123");
        assertEquals("John", user.getFname());
        assertEquals("Doe", user.getLname());
        assertEquals("john.doe@example.com", user.getMail());
        assertEquals("password123", user.getPw());
    }

    @Test
    public void testSetters() {
        User user = new User("John", "Doe", "john.doe@example.com", "password123");
        user.setFname("Jane");
        user.setLname("Smith");
        assertEquals("Jane", user.getFname());
        assertEquals("Smith", user.getLname());
    }
}
