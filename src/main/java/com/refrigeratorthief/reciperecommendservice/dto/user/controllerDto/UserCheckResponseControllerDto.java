package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@Validated
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserCheckResponseControllerDto {
    private Boolean success;
}