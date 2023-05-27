package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardControllerResponseDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardUpdateServiceResponseDto {
    private Integer id;
    private String title;
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Integer categoryId;
    private String categoryName;
    private String userId;
    private String userName;

    public BoardUpdateServiceResponseDto(Board board) {
        id = board.getId();
        title = board.getTitle();
        content = board.getContent();
        img = board.getImg();
        type = board.getType();
        createdDateTime = board.getCreatedDateTime();
        updatedDateTime = board.getUpdatedDateTime();
        categoryId = board.getCategory().getId();
        categoryName = board.getCategory().getName();
        userId = board.getUser().getId();
        userName = board.getUser().getName();
    }

    public BoardControllerResponseDto toController() {
        return BoardControllerResponseDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .img(img)
                .type(type)
                .createdDateTime(createdDateTime)
                .updatedDateTime(updatedDateTime)
                .categoryId(categoryId)
                .categoryName(categoryName)
                .userId(userId)
                .userName(userName)
                .build();
    }
}
