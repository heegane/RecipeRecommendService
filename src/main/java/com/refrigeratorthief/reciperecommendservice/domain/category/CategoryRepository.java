package com.refrigeratorthief.reciperecommendservice.domain.category;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    Optional<Category> findById(Integer id);

    Optional<Category> findByName(String name);

}
