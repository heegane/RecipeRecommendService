package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientAddRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    
    // 재료 직접 추가
    @PostMapping()
    public ResponseEntity<IngredientResponseControllerDto> addIngredient(
            @RequestBody IngredientAddRequestControllerDto ingredientAddRequestControllerDto){

        IngredientResponseServiceDto ingredientResponseServiceDto = ingredientService.addIngredient(ingredientAddRequestControllerDto.toServiceDto());

        return ResponseEntity.ok(ingredientResponseServiceDto.toControllerDto());
    }

}
