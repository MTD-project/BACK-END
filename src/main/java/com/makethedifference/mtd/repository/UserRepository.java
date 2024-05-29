package com.makethedifference.mtd.repository;

import com.makethedifference.mtd.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByCorreo(String correo);
}