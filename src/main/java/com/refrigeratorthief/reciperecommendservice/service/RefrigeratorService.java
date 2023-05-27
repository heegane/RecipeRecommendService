package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.IngredientRepository;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.RefrigeratorRepository;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorServiceResponseDto;
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
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RefrigeratorService {
    @Autowired
    private final RefrigeratorRepository refrigeratorRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final IngredientRepository ingredientRepository;


    // userId로 인자 받아서 해당하는 냉장고 속의 모든 재료 아이템 List로 반환
    @Transactional(readOnly = true)
    public List<RefrigeratorServiceResponseDto> getRefrigeratorAll(String userId) {

        User targetUser = userRepository.findById(userId)
                .orElseThrow(()->new CustomException("해당하는 유저 id가 존재하지 않습니다."));

        return refrigeratorRepository.findAllByUser(targetUser)
                .orElseThrow(()->new CustomException("해당하는 냉장고에 재료가 존재하지 않습니다."))
                .stream()
                .map(RefrigeratorServiceResponseDto::new)
                .collect(Collectors.toList());
    }

    // 냉장고 속 단일 식재료의 정보를 조회
    @Transactional(readOnly = true)
    public RefrigeratorServiceResponseDto getRefrigerator(Integer refrigeratorId) {
        
        Refrigerator targetRefrigerator = refrigeratorRepository.findById(refrigeratorId)
                .orElseThrow(()->new CustomException("해당하는 냉장고가 존재하지 않습니다."));

        return new RefrigeratorServiceResponseDto(targetRefrigerator);
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

        User targetUser = userRepository.findUserById(refrigeratorUpdateServiceRequestDto.getUser().getId())
                .orElseThrow(()->new CustomException("해당하는 유저가 존재하지 않습니다."));
        Ingredient targetIngredient = ingredientRepository.findById(refrigeratorUpdateServiceRequestDto.getIngredient().getId())
                .orElseThrow(()->new CustomException("해당하는 재료가 존재하지 않습니다."));
        Refrigerator targetRefri = refrigeratorRepository.findRefrigeratorByUserAndIngredient(targetUser, targetIngredient)
                .orElseThrow(()->new CustomException("해당하는 냉장고가 존재하지 않습니다."));

        targetRefri.updateFridge(refrigeratorUpdateServiceRequestDto.toEntity());

        refrigeratorRepository.save(targetRefri);

        return new RefrigeratorUpdateServiceResponseDto(targetRefri);
    }

    @Transactional
    public RefrigeratorDeleteServiceResponseDto deleteFridge(Integer id) {

        Refrigerator targetRefri = refrigeratorRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 냉장고가 존재하지 않습니다."));
        refrigeratorRepository.delete(targetRefri);

        return RefrigeratorDeleteServiceResponseDto.builder()
                .id(targetRefri.getId())
                .build();
    }
}