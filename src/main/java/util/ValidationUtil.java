package util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Simple server-side validation helpers returning a list of error messages.
 */
public class ValidationUtil {
    private static final Pattern EMAIL = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    public static List<String> validateRegistration(String username, String email, String password,
            String confirmPassword) {
        List<String> errors = new ArrayList<>();
        if (username == null || username.trim().length() < 3)
            errors.add("Username must be at least 3 characters.");
        if (email == null || !EMAIL.matcher(email).matches())
            errors.add("Enter a valid email address.");
        if (password == null || password.length() < 6)
            errors.add("Password must be at least 6 characters.");
        if (confirmPassword == null || !confirmPassword.equals(password))
            errors.add("Password and confirm password must match.");
        return errors;
    }

    public static List<String> validateLogin(String username, String password) {
        List<String> errors = new ArrayList<>();
        if (username == null || username.trim().isEmpty())
            errors.add("Username is required.");
        if (password == null || password.isEmpty())
            errors.add("Password is required.");
        return errors;
    }
}
