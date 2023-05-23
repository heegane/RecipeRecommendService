package com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardDeleteControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardDeleteServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorDeleteControllerResponseDto;
import lombok.*;

import java.util.Date;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefrigeratorDeleteServiceResponseDto {
    private Integer id;

    public RefrigeratorDeleteControllerResponseDto toControllerDto(RefrigeratorDeleteServiceResponseDto refrigeratorDeleteServiceResponseDto) {
        return RefrigeratorDeleteControllerResponseDto.builder()
                .id(refrigeratorDeleteServiceResponseDto.getId())
                .build();
    }
}
