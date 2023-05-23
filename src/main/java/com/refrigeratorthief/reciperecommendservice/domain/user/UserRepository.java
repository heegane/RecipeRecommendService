package com.refrigeratorthief.reciperecommendservice.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findUserById(String id);
    boolean existsUserById(String id);
    boolean existsUserByName(String id);
}