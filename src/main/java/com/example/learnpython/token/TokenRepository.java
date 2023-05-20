package com.example.learnpython.token;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenRepository extends JpaRepository<Token, Integer> {

    //@Transactional
    @Query(value = """
      select t from Token t inner join User u\s
      on t.user.id = u.id\s
      where u.id = :id and (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokenByUser(Long id);

    @Transactional
    @Query(value = "DELETE FROM token WHERE expired = true OR revoked = true", nativeQuery = true)
    void deleteAllInvalidTokens();

    //@Transactional
    Optional<Token> findByToken(String token);
}
