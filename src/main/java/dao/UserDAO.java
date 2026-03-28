package dao;

import java.util.List;
import model.User;

public interface UserDAO {
    void addUser(User user);
    User getUserById(int id);
    User getUserByUsername(String username);
    List<User> getAllUsers();
    void updateUser(User user);
    void deleteUser(int id);

    boolean validate(String username, String password);
}