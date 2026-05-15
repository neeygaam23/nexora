package service;

import dao.UserDao;
import model.User;
import util.PasswordUtil;

import java.util.Optional;

/**
 * Simple AuthService to illustrate MVC separation.
 */
public class AuthService {
    private UserDao userDao = new UserDao();

    public User authenticate(String username, String password) throws Exception {
        Optional<User> opt = userDao.findByUsername(username);
        if (opt.isPresent()) {
            User u = opt.get();
            if (PasswordUtil.verifyPassword(password, u.getPasswordHash())) {
                u.setPasswordHash(null);
                return u;
            }
        }
        return null;
    }
}