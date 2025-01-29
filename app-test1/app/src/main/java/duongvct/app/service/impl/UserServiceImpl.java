package duongvct.app.service.impl;

import duongvct.app.entity.User;
import duongvct.app.exception.MissingFieldException;
import duongvct.app.exception.UserAlreadyExistsException;
import duongvct.app.repository.UserRepository;
import duongvct.app.service.UserService;
import duongvct.app.utls.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRole().getId())
                .build();

    }

    @Override
    public void registerUser(User user) {
        String errorMessage = "";
        if (user.getEmail().isBlank()) {
            errorMessage += "Email is required. ";
        }
        if (user.getFullName().isBlank()) {
            errorMessage += "Fullname is required. ";
        }
        if (user.getPassword().isBlank()) {
            errorMessage += "Password is required. ";
        }
        if (user.getPhoneNumber().isBlank()) {
            errorMessage += "Phone number is required. ";
        }
        if (!errorMessage.isBlank()) {
            throw new MissingFieldException(errorMessage);
        }
        User existingUser = userRepository.findByEmail(user.getEmail()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("User already exists");
        }
//        if (user.getUsername().equals("admin")) {
//            user.setRole(Role.ROLE_ADMIN);
//        } else {
//            user.setRole(Role.ROLE_USER);
//        }
        user.setRole(Role.ROLE_USER);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Page<User> findAll(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public void updateUser(int id, User user) {
        User currentUser = userRepository.findById(id).orElse(null);
        if (currentUser == null) {
            throw new UsernameNotFoundException("User not found");
        }
        currentUser.setFullName(user.getFullName());
        currentUser.setPhoneNumber(user.getPhoneNumber());
        currentUser.setAvatar(user.getAvatar());
        userRepository.save(currentUser);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }


//    @Override
//    public void updateUser(User user) {
//        User currentUser = userRepository.findByUsername(user.getUsername()).orElse(null);
//        if (currentUser == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//
//
//    }
}
