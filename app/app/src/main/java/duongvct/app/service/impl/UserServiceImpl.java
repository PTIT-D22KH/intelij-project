package duongvct.app.service.impl;

import duongvct.app.entity.User;
import duongvct.app.exception.UserAlreadyExistsException;
import duongvct.app.repository.UserRepository;
import duongvct.app.service.UserService;
import duongvct.app.utls.Role;
import org.springframework.beans.factory.annotation.Autowired;
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
        User user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return org.springframework.security.core.userdetails.User.withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getRole().getId())
                .build();

    }

    @Override
    public void registerUser(User user) {
        User existingUser = userRepository.findByUsername(user.getUsername()).orElse(null);
        if (existingUser != null) {
            throw new UserAlreadyExistsException("Username already exists");
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
