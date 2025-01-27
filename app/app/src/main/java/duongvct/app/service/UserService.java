package duongvct.app.service;

import duongvct.app.entity.User;

import java.util.List;

public interface UserService {
    public void registerUser(User user);

    public List<User> findAll();

    public void updateUser(int id, User user);

    public User getUserById(int id);

    public void deleteUser(int id);
}
