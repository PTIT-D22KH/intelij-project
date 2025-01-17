package com.duongvct.repository;

import com.duongvct.entity.Account;
import com.duongvct.utils.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    public Optional<Account> findByUsername(String username);

    public void deleteById(Long id);

    public List<Account> findByRoles(Role role);

    @Query("SELECT a FROM Account a WHERE a.id = %:searchValue% AND a.roles = :role")
    List<Account> findUserById(@Param("searchValue") String searchValue, @Param("role") Role role);

    @Query("SELECT a FROM Account a WHERE a.username LIKE %:searchValue% AND a.roles = :role")
    List<Account> findUserByUsernameContaining(@Param("searchValue") String searchValue, @Param("role") Role role);

    @Query("SELECT a FROM Account a WHERE a.fullname LIKE %:searchValue% AND a.roles = :role")
    List<Account> findUserByFullnameContaining(@Param("searchValue") String searchValue, @Param("role") Role role);

    @Query("SELECT a FROM Account a WHERE a.email LIKE %:searchValue% AND a.roles = :role")
    List<Account> findUserByEmailContaining(@Param("searchValue") String searchValue, @Param("role") Role role);

    @Query("SELECT a FROM Account a WHERE a.phoneNumber LIKE %:searchValue% AND a.roles = :role")
    List<Account> findUserByPhoneNumberContaining(@Param("searchValue") String searchValue, @Param("role") Role role);

    @Query("SELECT a FROM Account a WHERE a.address LIKE %:searchValue% AND a.roles = :role")
    List<Account> findUserByAddressContaining(@Param("searchValue") String searchValue, @Param("role") Role role);

}
