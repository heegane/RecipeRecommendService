package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardDeleteControllerResponseDto;
import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardDeleteServiceResponseDto {
    private Integer id;

    public BoardDeleteControllerResponseDto toControllerDto(BoardDeleteServiceResponseDto boardDeleteServiceResponseDto) {
        return BoardDeleteControllerResponseDto.builder()
                .id(boardDeleteServiceResponseDto.getId())
                .build();
    }
}
