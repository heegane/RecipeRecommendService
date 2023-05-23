package com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit;

import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Integer> {

}
