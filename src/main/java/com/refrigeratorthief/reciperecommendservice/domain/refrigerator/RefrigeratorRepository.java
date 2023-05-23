package com.refrigeratorthief.reciperecommendservice.domain.refrigerator;

import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Integer> {
    Optional<Refrigerator> findById(Integer id);
}
