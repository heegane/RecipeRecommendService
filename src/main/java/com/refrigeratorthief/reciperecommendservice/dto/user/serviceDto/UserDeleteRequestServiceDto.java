package com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

@ToString
@Getter
@AllArgsConstructor
@Builder
@Data
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class UserDeleteRequestServiceDto {

    private String id;
    private String pw;
    private String checkPw;
}
