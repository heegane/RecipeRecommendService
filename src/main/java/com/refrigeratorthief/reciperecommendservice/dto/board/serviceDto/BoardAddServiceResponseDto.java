package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardControllerResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAddServiceResponseDto {
    private Integer id;
    private String title;
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String category;
    private String user;

    public BoardAddControllerResponseDto toControllerDto(BoardAddServiceResponseDto boardAddServiceResponseDto) {
        return BoardAddControllerResponseDto.builder()
                .id(boardAddServiceResponseDto.getId())
                .title(boardAddServiceResponseDto.getTitle())
                .content(boardAddServiceResponseDto.getContent())
                .img(boardAddServiceResponseDto.getImg())
                .type(boardAddServiceResponseDto.getType())
                .createdDateTime(boardAddServiceResponseDto.getCreatedDateTime())
                .updatedDateTime(boardAddServiceResponseDto.getUpdatedDateTime())
                .category(boardAddServiceResponseDto.getCategory())
                .user(boardAddServiceResponseDto.getUser())
                .build();
    }
}
