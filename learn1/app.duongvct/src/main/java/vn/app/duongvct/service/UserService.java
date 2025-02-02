package vn.app.duongvct.service;

import org.springframework.stereotype.Service;
import vn.app.duongvct.domain.User;
import vn.app.duongvct.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String handleHello() {
        return "hello from service";
    }

    public User handleSaveUser(User user) {
        User eric =  this.userRepository.save(user);
        return eric;
    }

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }
    public List<User> getAllUsersByEmail(String email) {
        return this.userRepository.findOneByEmail(email);
    }

    public User getUserById(Long id) {
        return this.userRepository.findById(id);
    }
}
