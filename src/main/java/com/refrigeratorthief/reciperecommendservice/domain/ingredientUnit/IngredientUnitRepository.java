package com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit;

import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface IngredientUnitRepository extends JpaRepository<IngredientUnit, Integer> {
    Optional<IngredientUnit> findIngredientUnitById(Integer id);

}
