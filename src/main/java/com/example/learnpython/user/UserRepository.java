package com.example.learnpython.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role=:role")
    List<User> findByRole(Role role);

    @Query("UPDATE User u SET u.exp=:exp, u.level=:level WHERE u.id=:id")
    void updateLevelAndExpById(final Long id, final Integer level, final Long exp);

    @Query("DELETE FROM User u WHERE u.email=:email")
    void deleteByEmail(final String email);
}
