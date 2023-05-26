package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.IngredientRepository;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnitRepository;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientAddRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class IngredientService {

    private final int batchSize = 100;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final IngredientRepository ingredientRepository;

    @Autowired
    private final IngredientUnitRepository ingredientUnitRepository;

    @Transactional
    public IngredientResponseServiceDto addIngredient(IngredientAddRequestServiceDto ingredientAddRequestServiceDto) {

        if(ingredientRepository.existsIngredientByName(ingredientAddRequestServiceDto.getName())) {
            throw new CustomException("동일한 이름의 재료가 존재합니다!");
        }

        Integer ingredientUnitId = ingredientAddRequestServiceDto.getIngredientUnit().getId();
        IngredientUnit targetIngredientUnit = ingredientUnitRepository.findIngredientUnitById(ingredientUnitId)
                .orElseThrow(()->new CustomException("해당하는 재료 단위가 없습니다."));

        Ingredient result = ingredientRepository.save(ingredientAddRequestServiceDto.toEntity(targetIngredientUnit));
        return new IngredientResponseServiceDto(result);
    }
}
