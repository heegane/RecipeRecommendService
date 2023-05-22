package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorControllerResponseDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorServiceResponseDto {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String user;
    private Integer ingredient;

    public RefrigeratorControllerResponseDto toControllerDto(RefrigeratorServiceResponseDto refrigeratorServiceResponseDto) {
        return RefrigeratorControllerResponseDto.builder()
                .id(refrigeratorServiceResponseDto.getId())
                .expirationDate(refrigeratorServiceResponseDto.getExpirationDate())
                .quantity(refrigeratorServiceResponseDto.getQuantity())
                .location(refrigeratorServiceResponseDto.getLocation())
                .user(refrigeratorServiceResponseDto.getUser())
                .ingredient(refrigeratorServiceResponseDto.getIngredient())
                .build();
    }
}
