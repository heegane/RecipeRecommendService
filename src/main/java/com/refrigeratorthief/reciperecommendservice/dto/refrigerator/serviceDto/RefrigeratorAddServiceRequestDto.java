package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorAddServiceRequestDto {
    private Date expirationDate;
    private Integer quantity;
    private String location;
    private String user;
    private Integer ingredient;
}
