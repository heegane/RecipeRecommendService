package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.IngredientRepository;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.RefrigeratorRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardDeleteServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;

@RequiredArgsConstructor
@Service
public class RefrigeratorService {
    @Autowired
    private final RefrigeratorRepository refrigeratorRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final IngredientRepository ingredientRepository;

    @Transactional(readOnly = true)
    public RefrigeratorServiceResponseDto getRefrigerator(Integer id) {
        Refrigerator targetRefri = refrigeratorRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 냉장고가 존재하지 않습니다."));

        return RefrigeratorServiceResponseDto.builder()
                .id(targetRefri.getId())
                .expirationDate(targetRefri.getExpirationDate())
                .quantity(targetRefri.getQuantity())
                .location(targetRefri.getLocation())
                .userId(targetRefri.getUser().getId())
                .userName(targetRefri.getUser().getName())
                .ingredientId(targetRefri.getIngredient().getId())
                .ingredientName(targetRefri.getIngredient().getName())
                .ingredientImg(targetRefri.getIngredient().getImg())
                .ingredientUnitId(targetRefri.getIngredient().getUnit().getId())
                .ingredientUnitName(targetRefri.getIngredient().getUnit().getName())
                .build();
    }

    @Transactional
    public RefrigeratorAddServiceResponseDto addFridge(RefrigeratorAddServiceRequestDto refrigeratorAddServiceRequestDto) {
        Ingredient targetIngredient = ingredientRepository.findById(refrigeratorAddServiceRequestDto.getIngredientId())
                .orElseThrow(()->new CustomException("해당하는 재료가 존재하지 않습니다."));
        User targetUser = userRepository.findById(refrigeratorAddServiceRequestDto.getUserId())
                .orElseThrow(()->new CustomException("해당하는 id가 존재하지 않습니다."));

        Refrigerator targetRefri = Refrigerator.builder()
                .expirationDate(refrigeratorAddServiceRequestDto.getExpirationDate())
                .quantity(refrigeratorAddServiceRequestDto.getQuantity())
                .location(refrigeratorAddServiceRequestDto.getLocation())
                .user(targetUser)
                .ingredient(targetIngredient)
                .build();

        refrigeratorRepository.save(targetRefri);

        return RefrigeratorAddServiceResponseDto.builder()
                .id(targetRefri.getId())
                .expirationDate(targetRefri.getExpirationDate())
                .quantity(targetRefri.getQuantity())
                .location(targetRefri.getLocation())
                .userId(targetRefri.getUser().getId())
                .userName(targetRefri.getUser().getName())
                .ingredientId(targetRefri.getIngredient().getId())
                .ingredientName(targetRefri.getIngredient().getName())
                .ingredientImg(targetRefri.getIngredient().getImg())
                .ingredientUnitId(targetRefri.getIngredient().getUnit().getId())
                .ingredientUnitName(targetRefri.getIngredient().getUnit().getName())
                .build();
    }

    @Transactional
    public RefrigeratorUpdateServiceResponseDto updateFridge(RefrigeratorUpdateServiceRequestDto refrigeratorUpdateServiceRequestDto) {
        Ingredient targetIngredient = ingredientRepository.findById(refrigeratorUpdateServiceRequestDto.getIngredientId())
                .orElseThrow(()->new CustomException("해당하는 재료가 존재하지 않습니다."));
        User targetUser = userRepository.findById(refrigeratorUpdateServiceRequestDto.getUserId())
                .orElseThrow(()->new CustomException("해당하는 id가 존재하지 않습니다."));
        Refrigerator targetRefri = refrigeratorRepository.findById(refrigeratorUpdateServiceRequestDto.getId())
                .orElseThrow(()->new CustomException("해당하는 냉장고가 존재하지 않습니다."));

        Refrigerator resultFridge = Refrigerator.builder()
                .id(targetRefri.getId())
                .expirationDate(refrigeratorUpdateServiceRequestDto.getExpirationDate())
                .quantity(refrigeratorUpdateServiceRequestDto.getQuantity())
                .location(refrigeratorUpdateServiceRequestDto.getLocation())
                .user(targetUser)
                .ingredient(targetIngredient)
                .build();

        targetRefri.updateFridge(resultFridge);

        return RefrigeratorUpdateServiceResponseDto.builder()
                .id(resultFridge.getId())
                .expirationDate(resultFridge.getExpirationDate())
                .quantity(resultFridge.getQuantity())
                .location(resultFridge.getLocation())
                .userId(resultFridge.getUser().getId())
                .userName(resultFridge.getUser().getName())
                .ingredientId(resultFridge.getIngredient().getId())
                .ingredientName(resultFridge.getIngredient().getName())
                .ingredientImg(resultFridge.getIngredient().getImg())
                .ingredientUnitId(resultFridge.getIngredient().getUnit().getId())
                .ingredientUnitName(resultFridge.getIngredient().getUnit().getName())
                .build();
    }

    @Transactional
    public RefrigeratorDeleteServiceResponseDto deleteFridge(Integer id) {
        if (refrigeratorRepository.findById(id).isEmpty()) {
            throw new CustomException("해당하는 냉장고가 존재하지 않습니다.");
        }
        Refrigerator targetRefri = refrigeratorRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 냉장고가 존재하지 않습니다."));
        refrigeratorRepository.delete(targetRefri);

        return RefrigeratorDeleteServiceResponseDto.builder()
                .id(targetRefri.getId())
                .build();
    }
}