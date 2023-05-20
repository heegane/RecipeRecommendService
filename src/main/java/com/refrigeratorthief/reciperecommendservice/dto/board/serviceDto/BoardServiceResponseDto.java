package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardControllerResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardServiceResponseDto {
    private Integer id;
    private String title;
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String category;
    private String user;

    public BoardControllerResponseDto toControllerDto(BoardServiceResponseDto boardServiceResponseDto) {
        return BoardControllerResponseDto.builder()
                .id(boardServiceResponseDto.getId())
                .title(boardServiceResponseDto.getTitle())
                .content(boardServiceResponseDto.getContent())
                .img(boardServiceResponseDto.getImg())
                .type(boardServiceResponseDto.getType())
                .createdDateTime(boardServiceResponseDto.getCreatedDateTime())
                .updatedDateTime(boardServiceResponseDto.getUpdatedDateTime())
                .category(boardServiceResponseDto.getCategory())
                .user(boardServiceResponseDto.getUser())
                .build();
    }

}
