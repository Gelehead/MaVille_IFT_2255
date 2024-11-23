package UI_UX;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class SpeakerTest {

    @Test
    void testValidEmails() {
        assertTrue(Speaker.isValidEmail("example@example.com"));
        assertTrue(Speaker.isValidEmail("user.name+tag@example.com"));
        assertTrue(Speaker.isValidEmail("test_email123@example.org"));
    }

    @Test
    void testInvalidEmails() {
        assertFalse(Speaker.isValidEmail("adresseseulement"));
        assertFalse(Speaker.isValidEmail("@sansusername.com"));
        assertFalse(Speaker.isValidEmail("username@.com"));
        assertFalse(Speaker.isValidEmail("user@site.c"));
    }


    @Test
    void testValidPasswords() {
        assertTrue(Speaker.isSecurePassword("Zero@Hackage88!"));
        assertTrue(Speaker.isSecurePassword("P@ssw0rdSecure123!"));
        assertTrue(Speaker.isSecurePassword("Valid#Password1"));
        assertTrue(Speaker.isSecurePassword("Secure@2024"));
    }

    @Test
    void testInvalidPasswords() {
        assertFalse(Speaker.isSecurePassword("petiti"));
        assertFalse(Speaker.isSecurePassword("toutminusclule"));
        assertFalse(Speaker.isSecurePassword("TOUTMAJUSCULE"));
        assertFalse(Speaker.isSecurePassword("NoSpecial123"));
        assertFalse(Speaker.isSecurePassword("Je fut hacker123!"));
    }

}