package vn.app.duongvct.service;

import org.springframework.stereotype.Service;
import vn.app.duongvct.domain.Role;
import vn.app.duongvct.domain.User;
import vn.app.duongvct.repository.RoleRepository;
import vn.app.duongvct.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
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
        return this.userRepository.findById(id).orElse(null);
    }

    public void deleteUserById(Long id) {
        this.userRepository.deleteById(id);

    }
    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }
}
