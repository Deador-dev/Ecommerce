package com.plocky.deador.repository;

import com.plocky.deador.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findUserByEmail(String email);
    User findUserByEmailContains(String email);

}
