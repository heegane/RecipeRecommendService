package com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAddControllerRequestDto {
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String category;
    private String user;

    public BoardAddServiceRequestDto toServiceDto(BoardAddControllerRequestDto boardAddControllerRequestDto) {
        return BoardAddServiceRequestDto.builder()
                .content(boardAddControllerRequestDto.getContent())
                .img(boardAddControllerRequestDto.getImg())
                .type(boardAddControllerRequestDto.getType())
                .createdDateTime(boardAddControllerRequestDto.getCreatedDateTime())
                .updatedDateTime(boardAddControllerRequestDto.getUpdatedDateTime())
                .category(boardAddControllerRequestDto.getCategory())
                .user(boardAddControllerRequestDto.getUser())
                .build();
    }
}
