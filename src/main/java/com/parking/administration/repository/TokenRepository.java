package com.parking.administration.repository;

import com.parking.administration.domain.token.Token;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Repository
@Transactional
public interface TokenRepository extends JpaRepository<Token, Long> {

    Optional<Token> findByToken(String token);

    @Transactional
    @Modifying
    @Query("UPDATE Token c " +
            "SET c.confirmedAt = ?2 " +
            "WHERE c.token = ?1")
    void updateConfirmedAt(String token, LocalDateTime confirmedAt);

    @Query(value = """
      SELECT t FROM Token t INNER JOIN User user\s
      ON t.user.id = user.id\s
      WHERE user.id = :id AND (t.expired = false or t.revoked = false)\s
      """)
    List<Token> findAllValidTokeByUser(Long id);
}
