package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientAddRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientTinyResponseControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientAddRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.service.IngredientService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.amazonaws.util.AWSRequestMetrics.Field.StatusCode;

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

    @PostMapping()
    public ResponseEntity<IngredientResponseControllerDto> addIngredient(
            @RequestParam(value = "name", required = true) String name,
            @RequestParam(value = "img", required = false) MultipartFile multipartFile,
            @RequestParam(value = "ingredient_unit_id", required = true) Integer ingredientUnitId
    ) throws IOException {

        Ingredient ingredient = Ingredient.builder()
                .name(name)
                .unit(IngredientUnit.builder().id(ingredientUnitId).build())
                .build();

        ingredientService.imgSave(ingredient, multipartFile);

        IngredientAddRequestServiceDto ingredientAddRequestServiceDto = IngredientAddRequestServiceDto.builder()
                .name(ingredient.getName())
                .img(ingredient.getImg())
                .ingredientUnit(ingredient.getUnit())
                .build();

        IngredientResponseServiceDto ingredientResponseServiceDto = ingredientService.addIngredient(ingredientAddRequestServiceDto);

        return ResponseEntity.ok(ingredientResponseServiceDto.toControllerDto());
    }
}
