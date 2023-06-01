
package com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.validation.Valid;

@ToString
@AllArgsConstructor
@Builder
@Data
@Valid
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserNicknameCheckRequestControllerDto {
    private String name;

    public UserNicknameCheckRequestControllerDto() {

    }
}
