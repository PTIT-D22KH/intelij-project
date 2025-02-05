package vn.hoidanit.laptopshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import vn.hoidanit.laptopshop.domain.User;

// crud: create, read, uodate, delete
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User save(User eric);

    List<User> findOneByEmail(String email);

    List<User> findAll();

    User findById(long id);

    void deleteById(long id);

    // check email exist ?
    boolean existsByEmail(String email);

    User findByEmail(String email);

}
