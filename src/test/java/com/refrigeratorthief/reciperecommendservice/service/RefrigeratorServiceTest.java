package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.IngredientRepository;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.RefrigeratorRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Ref;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ComponentScan
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class RefrigeratorServiceTest {
    @InjectMocks
    private RefrigeratorService refrigeratorService;
    @Mock
    private RefrigeratorRepository refrigeratorRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private IngredientRepository ingredientRepository;
    private final TestUtils testUtils = new TestUtils();

    @Test
    void getRefrigerator() {
        //given
        Refrigerator targetRef = testUtils.getTestRef();
        doReturn(Optional.of(targetRef)).when(refrigeratorRepository).findById(targetRef.getId());

        //when
        RefrigeratorServiceResponseDto result = refrigeratorService.getRefrigerator(targetRef.getId());

        //then
        Assertions.assertNotNull(result);
        verify(refrigeratorRepository).findById(targetRef.getId());
    }

    @Test
    void addFridge() {
        //given
        Refrigerator targetRef = testUtils.getTestRef();

        RefrigeratorAddServiceRequestDto refrigeratorAddServiceRequestDto = RefrigeratorAddServiceRequestDto
                .builder()
                .expirationDate(targetRef.getExpirationDate())
                .quantity(targetRef.getQuantity())
                .location(targetRef.getLocation())
                .userId(targetRef.getUser().getId())
                .ingredientId(targetRef.getIngredient().getId())
                .build();

        Refrigerator testEntity = refrigeratorAddServiceRequestDto.toEntity();

        doReturn(Optional.of(targetRef.getUser())).when(userRepository).findById(any());
        doReturn(Optional.of(targetRef.getIngredient())).when(ingredientRepository).findById(any());
        doReturn(testEntity).when(refrigeratorRepository).save(any());

        //when
        RefrigeratorAddServiceResponseDto result = refrigeratorService.addFridge(refrigeratorAddServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        verify(userRepository).findById(any());
        verify(ingredientRepository).findById(any());
        verify(refrigeratorRepository).save(any());
    }

    @Test
    void updateFridge() {
        //given
        Refrigerator targetRef = testUtils.getTestRef();

        doReturn(Optional.of(targetRef.getIngredient())).when(ingredientRepository).findById(any());
        doReturn(Optional.of(targetRef.getUser())).when(userRepository).findById(any());
        doReturn(Optional.of(targetRef)).when(refrigeratorRepository).findById(any());

        RefrigeratorUpdateServiceRequestDto refrigeratorUpdateServiceRequestDto = RefrigeratorUpdateServiceRequestDto
                .builder()
                .id(targetRef.getId())
                .expirationDate(targetRef.getExpirationDate())
                .quantity(targetRef.getQuantity())
                .location("수정된 위치")
                .userId(targetRef.getUser().getId())
                .ingredientId(targetRef.getIngredient().getId())
                .build();

        //when
        RefrigeratorUpdateServiceResponseDto result = refrigeratorService.updateFridge(refrigeratorUpdateServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("수정된 위치", result.getLocation());
        verify(ingredientRepository).findById(any());
        verify(userRepository).findById(any());
        verify(refrigeratorRepository).findById(any());
    }

    @Test
    void deleteFridge() {
        //given
        Refrigerator targetRef = testUtils.getTestRef();
        doReturn(Optional.of(targetRef)).when(refrigeratorRepository).findById(targetRef.getId());

        //when
        RefrigeratorDeleteServiceResponseDto result = refrigeratorService.deleteFridge(targetRef.getId());

        //then
        Assertions.assertNotNull(result);
        verify(refrigeratorRepository).delete(targetRef);
    }
}