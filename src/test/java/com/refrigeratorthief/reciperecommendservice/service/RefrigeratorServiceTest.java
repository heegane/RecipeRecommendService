package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
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
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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
    void getRefrigeratorAll() {
        //given
        User testUser = testUtils.getTestUser();
        String userId = testUtils.getTestUser().getId();
        List<Refrigerator> testList = new ArrayList<>();

        testList.add(testUtils.getTestRef2());
        testList.add(testUtils.getTestRef3());

        doReturn(Optional.of(testUser)).when(userRepository).findById(userId);
        doReturn(Optional.of(testList)).when(refrigeratorRepository).findAllByUser(testUser);

        //when
        List<RefrigeratorServiceResponseDto> results = refrigeratorService.getRefrigeratorAll(userId);

        //then
        Assertions.assertNotNull(results);
        Assertions.assertEquals(testList.stream().map(RefrigeratorServiceResponseDto::new).collect(Collectors.toList()).toString(), Objects.requireNonNull(results).toString());
        verify(refrigeratorRepository).findAllByUser(testUser);
    }

    @Test
    void getRefrigerator() {
        //given
        Integer testRefrigeratorId = testUtils.getTestRef().getId();
        Refrigerator testRefrigerator = testUtils.getTestRef();

        doReturn(Optional.of(testRefrigerator)).when(refrigeratorRepository).findById(testRefrigeratorId);

        //when
        RefrigeratorServiceResponseDto result = refrigeratorService.getRefrigerator(testRefrigeratorId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.toString(), Objects.requireNonNull(result).toString());
        verify(refrigeratorRepository).findById(testRefrigeratorId);
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
        java.sql.Date testDate = java.sql.Date.valueOf("2028-05-05");

        doReturn(Optional.of(targetRef.getUser())).when(userRepository).findUserById(anyString());
        doReturn(Optional.of(targetRef.getIngredient())).when(ingredientRepository).findById(anyInt());
        doReturn(Optional.of(targetRef)).when(refrigeratorRepository).findRefrigeratorByUserAndIngredient(any(User.class),any(Ingredient.class));

        RefrigeratorUpdateServiceRequestDto refrigeratorUpdateServiceRequestDto = RefrigeratorUpdateServiceRequestDto
                .builder()
                .expirationDate(testDate)
                .quantity(100)
                .location("수정된 위치")
                .user(targetRef.getUser())
                .ingredient(targetRef.getIngredient())
                .build();

        //when
        RefrigeratorUpdateServiceResponseDto result = refrigeratorService.updateFridge(refrigeratorUpdateServiceRequestDto);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(testDate,result.getExpirationDate());
        Assertions.assertEquals("수정된 위치", result.getLocation());
        Assertions.assertEquals(100,result.getQuantity());
        verify(userRepository).findUserById(anyString());
        verify(ingredientRepository).findById(anyInt());
        verify(refrigeratorRepository).findRefrigeratorByUserAndIngredient(any(User.class), any(Ingredient.class));
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