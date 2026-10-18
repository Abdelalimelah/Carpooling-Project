package com.JAVA.Utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordUtilTest {

    @Test
    void testGenerateSalt_UniqueAndLength() {
        String salt1 = PasswordUtil.generateSalt();
        String salt2 = PasswordUtil.generateSalt();
        assertNotNull(salt1);
        assertNotNull(salt2);
        assertNotEquals(salt1, salt2, "Salts should be unique");
        assertTrue(salt1.length() >= 22, "Salt should be at least 16 bytes in Base64");
    }

    @Test
    void testHashPassword_Consistency() {
        String salt = PasswordUtil.generateSalt();
        String hash1 = PasswordUtil.hashPassword("password123", salt);
        String hash2 = PasswordUtil.hashPassword("password123", salt);
        assertEquals(hash1, hash2, "Hashing same password and salt should be consistent");
    }

    @Test
    void testHashPassword_DifferentSalts() {
        String hash1 = PasswordUtil.hashPassword("password123", PasswordUtil.generateSalt());
        String hash2 = PasswordUtil.hashPassword("password123", PasswordUtil.generateSalt());
        assertNotEquals(hash1, hash2, "Hashes with different salts should differ");
    }

    @Test
    void testHashPasswordWithSalt_Format() {
        String result = PasswordUtil.hashPasswordWithSalt("password123");
        assertNotNull(result);
        String[] parts = result.split("\\$");
        assertEquals(2, parts.length, "Should contain salt and hash separated by $ symbol");
    }

    @Test
    void testVerifyPassword_CorrectAndIncorrect() {
        String password = "mySecret";
        String stored = PasswordUtil.hashPasswordWithSalt(password);
        assertTrue(PasswordUtil.verifyPassword(password, stored), "Correct password should verify");
        assertFalse(PasswordUtil.verifyPassword("wrongPassword", stored), "Incorrect password should not verify");
    }

    @Test
    void testVerifyPassword_InvalidFormat() {
        assertFalse(PasswordUtil.verifyPassword("any", "invalidformat"), "Should return false for invalid stored format");
    }
}
