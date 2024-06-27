package com.makethedifference.mtd.infra.repository;

import com.makethedifference.mtd.infra.email.PasswordResetToken;
import  org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByEmail(String email);
    void deleteByEmail(String email);

}
