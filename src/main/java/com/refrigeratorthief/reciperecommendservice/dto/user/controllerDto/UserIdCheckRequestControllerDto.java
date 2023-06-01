package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@ToString
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@Validated
public class UserIdCheckRequestControllerDto {
    private String id;

    public UserIdCheckRequestControllerDto() {

    }
}
