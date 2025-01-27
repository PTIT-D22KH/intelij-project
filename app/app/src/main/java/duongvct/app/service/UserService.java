package duongvct.app.service;

import duongvct.app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    public void registerUser(User user);

    public List<User> findAll();
    public Page<User> findAll(Pageable pageable);

    public void updateUser(int id, User user);

    public User getUserById(int id);

    public void deleteUser(int id);
}
