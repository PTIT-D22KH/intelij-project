package vn.app.duongvct.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.app.duongvct.domain.User;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User save(User hoidanit);
    List<User> findOneByEmail(String email);

}
