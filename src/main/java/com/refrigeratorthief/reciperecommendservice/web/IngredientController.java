package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientAddRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientTinyResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/ingredient")
public class IngredientController {

    private final IngredientService ingredientService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 모든 재료 조회
    @GetMapping()
    public ResponseEntity<List<IngredientTinyResponseControllerDto>> getIngredientAll(){

        List<IngredientTinyResponseControllerDto> results;

        results = ingredientService.getIngredientAll()
                .stream()
                .map(IngredientResponseServiceDto::toTinyControllerDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(results);
    }

    // 재료 직접 추가
    @PostMapping()
    public ResponseEntity<IngredientResponseControllerDto> addIngredient(
            @RequestBody IngredientAddRequestControllerDto ingredientAddRequestControllerDto){

        IngredientResponseServiceDto ingredientResponseServiceDto = ingredientService.addIngredient(ingredientAddRequestControllerDto.toServiceDto());

        return ResponseEntity.ok(ingredientResponseServiceDto.toControllerDto());
    }

}
