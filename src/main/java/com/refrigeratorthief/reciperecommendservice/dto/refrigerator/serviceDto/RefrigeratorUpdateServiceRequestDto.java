package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorUpdateServiceRequestDto {
    private Integer id;
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String userId;
    private Integer ingredientId;
}
