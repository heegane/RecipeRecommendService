package com.refrigeratorthief.reciperecommendservice.domain.refrigerator;

import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RefrigeratorRepository extends JpaRepository<Refrigerator, Integer> {
    Optional<Refrigerator> findById(Integer id);
    Optional<List<Refrigerator>> findAllByUser(User user);
    Optional<Refrigerator> findRefrigeratorByUserAndIngredient(User user, Ingredient ingredient);
}
