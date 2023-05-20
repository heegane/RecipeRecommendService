package com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto;

import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardUpdateServiceRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateControllerRequestDto {
    private Integer id;
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String category;
    private String member;

    public BoardUpdateServiceRequestDto toServiceDto(BoardUpdateControllerRequestDto boardUpdateControllerRequestDto) {
        return  BoardUpdateServiceRequestDto.builder()
                .id(boardUpdateControllerRequestDto.getId())
                .content(boardUpdateControllerRequestDto.getContent())
                .img(boardUpdateControllerRequestDto.getImg())
                .type(boardUpdateControllerRequestDto.getType())
                .category(boardUpdateControllerRequestDto.getCategory())
                .member(boardUpdateControllerRequestDto.getMember())
                .build();
    }
}
