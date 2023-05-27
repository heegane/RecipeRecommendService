package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.IngredientRepository;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnit;
import com.refrigeratorthief.reciperecommendservice.domain.ingredientUnit.IngredientUnitRepository;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientAddRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ComponentScan
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class IngredientServiceTest {

    @InjectMocks
    private IngredientService ingredientService;

    @Mock
    private IngredientRepository ingredientRepository;

    @Mock
    private IngredientUnitRepository ingredientUnitRepository;

    private final TestUtils testUtils = new TestUtils();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void getIngredientAll() {
        //given
        List<Ingredient> testList = new ArrayList<>();
        testList.add(testUtils.getTestIngredient());
        testList.add(testUtils.getTestIngredient2());

        doReturn(Optional.of(testList)).when(ingredientRepository).findAllBy();

        //when
        List<IngredientResponseServiceDto> results = ingredientService.getIngredientAll();

        //then
        Assertions.assertNotNull(results);
        Assertions.assertEquals(testList.stream().map(IngredientResponseServiceDto::new).collect(Collectors.toList()).toString(), Objects.requireNonNull(results).toString());
        verify(ingredientRepository).findAllBy();
    }

    @Test
    void addIngredient() {
        //given
        IngredientAddRequestServiceDto ingredientAddRequestServiceDto = IngredientAddRequestServiceDto
                .builder()
                .name(testUtils.getTestIngredient().getName())
                .img(testUtils.getTestIngredient().getImg())
                .ingredientUnit(testUtils.getTestIngredientUnit())
                .build();

        Ingredient testEntity = ingredientAddRequestServiceDto.toEntity(ingredientAddRequestServiceDto.getIngredientUnit());
        testEntity.setId(1);
        IngredientUnit testIngredientUnit = testUtils.getTestIngredientUnit();

        doReturn(Optional.of(testIngredientUnit)).when(ingredientUnitRepository).findIngredientUnitById(anyInt());
        doReturn(testEntity).when(ingredientRepository).save(any(Ingredient.class));

        //when
        IngredientResponseServiceDto result = ingredientService.addIngredient(ingredientAddRequestServiceDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.toString(), Objects.requireNonNull(result).toString());
        verify(ingredientUnitRepository).findIngredientUnitById(anyInt());
        verify(ingredientRepository).save(any(Ingredient.class));
    }

    @Test
    void duplicatedNameErrorWhenAddIngredient() {

        //given
        IngredientAddRequestServiceDto ingredientAddRequestServiceDto = IngredientAddRequestServiceDto
                .builder()
                .name(testUtils.getTestIngredient().getName())
                .img(testUtils.getTestIngredient().getImg())
                .ingredientUnit(testUtils.getTestIngredientUnit())
                .build();

        IngredientUnit testIngredientUnit = testUtils.getTestIngredientUnit();

        doReturn(true).when(ingredientRepository).existsIngredientByName(ingredientAddRequestServiceDto.getName());

        //when
        CustomException result = assertThrows(CustomException.class, ()-> ingredientService.addIngredient(ingredientAddRequestServiceDto));

        //then
        assertEquals("동일한 이름의 재료가 존재합니다!",result.getMessage());
        verify(ingredientRepository).existsIngredientByName(ingredientAddRequestServiceDto.getName());
    }
}
