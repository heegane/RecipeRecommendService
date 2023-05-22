package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorAddControllerResponseDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorAddServiceResponseDto {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String user;
    private Integer ingredient;

    public RefrigeratorAddControllerResponseDto toControllerDto(RefrigeratorAddServiceResponseDto refrigeratorAddServiceResponseDto) {
        return RefrigeratorAddControllerResponseDto.builder()
                .id(refrigeratorAddServiceResponseDto.getId())
                .expirationDate(refrigeratorAddServiceResponseDto.getExpirationDate())
                .quantity(refrigeratorAddServiceResponseDto.getQuantity())
                .location(refrigeratorAddServiceResponseDto.getLocation())
                .user(refrigeratorAddServiceResponseDto.getUser())
                .ingredient(refrigeratorAddServiceResponseDto.getIngredient())
                .build();
    }
}