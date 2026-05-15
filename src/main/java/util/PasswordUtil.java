package util;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Password utilities using PBKDF2 for hashing and verification.
 * Beginner-friendly and secure for coursework purposes.
 */
public class PasswordUtil {
    private static final String ALGORITHM = "PBKDF2WithHmacSHA256";
    private static final int ITERATIONS = 10000;
    private static final int KEY_LENGTH = 256; // bits
    private static final SecureRandom RANDOM = new SecureRandom();

    // Generate a random salt
    public static String generateSalt() {
        byte[] salt = new byte[16];
        RANDOM.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    // Hash a plain password with provided salt and return Base64 hash
    public static String hash(char[] password, String salt) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] saltBytes = Base64.getDecoder().decode(salt);
        PBEKeySpec spec = new PBEKeySpec(password, saltBytes, ITERATIONS, KEY_LENGTH);
        SecretKeyFactory skf = SecretKeyFactory.getInstance(ALGORITHM);
        byte[] hash = skf.generateSecret(spec).getEncoded();
        return Base64.getEncoder().encodeToString(hash);
    }

    // Creates a storage string combining salt and hash: salt:hash
    public static String createStoredPassword(String plainPassword)
            throws NoSuchAlgorithmException, InvalidKeySpecException {
        String salt = generateSalt();
        String hashed = hash(plainPassword.toCharArray(), salt);
        return salt + ":" + hashed;
    }

    // Verify a plain password against stored value salt:hash
    public static boolean verifyPassword(String plainPassword, String stored) {
        try {
            if (stored == null || !stored.contains(":"))
                return false;
            String[] parts = stored.split(":");
            if (parts.length != 2)
                return false;
            String salt = parts[0];
            String hash = parts[1];
            String computed = hash(plainPassword.toCharArray(), salt);
            return slowEquals(computed, hash);
        } catch (Exception e) {
            return false;
        }
    }

    // Constant-time comparison to prevent timing attacks
    private static boolean slowEquals(String a, String b) {
        byte[] aa = a.getBytes();
        byte[] bb = b.getBytes();
        int diff = aa.length ^ bb.length;
        for (int i = 0; i < Math.min(aa.length, bb.length); i++) {
            diff |= aa[i] ^ bb[i];
        }
        return diff == 0;
    }
}
