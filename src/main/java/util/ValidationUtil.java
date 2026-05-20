package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Utility class for simple server-side validation.
 * Returns a list of error messages instead of throwing exceptions.
 */
public class ValidationUtil {

    // Basic email validation pattern
    private static final Pattern EMAIL_PATTERN =
            Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    /**
     * Validates user registration input fields.
     */
    public static List<String> validateRegistration(
            String username,
            String email,
            String password,
            String confirmPassword
    ) {

        List<String> errors = new ArrayList<>();

        // Username check
        if (username == null || username.trim().length() < 3) {
            errors.add("Username must be at least 3 characters.");
        }

        // Email format check
        if (email == null || !EMAIL_PATTERN.matcher(email).matches()) {
            errors.add("Enter a valid email address.");
        }

        // Password length check
        if (password == null || password.length() < 6) {
            errors.add("Password must be at least 6 characters.");
        }

        // Confirm password match check
        if (confirmPassword == null || !confirmPassword.equals(password)) {
            errors.add("Password and confirm password must match.");
        }

        return errors;
    }

    /**
     * Validates login input fields.
     */
    public static List<String> validateLogin(String username, String password) {

        List<String> errors = new ArrayList<>();

        // Username must not be empty
        if (username == null || username.trim().isEmpty()) {
            errors.add("Username is required.");
        }

        // Password must not be empty
        if (password == null || password.isEmpty()) {
            errors.add("Password is required.");
        }

        return errors;
    }
}