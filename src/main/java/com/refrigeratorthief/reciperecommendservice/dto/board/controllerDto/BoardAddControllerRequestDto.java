package com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto;

import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardAddServiceRequestDto;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoardAddControllerRequestDto {
    private String title;
    private String content;
    private String img;
    private String type;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private Integer category;
    private String user;

    public BoardAddServiceRequestDto toServiceDto(BoardAddControllerRequestDto boardAddControllerRequestDto) {
        return BoardAddServiceRequestDto.builder()
                .title(boardAddControllerRequestDto.getTitle())
                .content(boardAddControllerRequestDto.getContent())
                .img(boardAddControllerRequestDto.getImg())
                .type(boardAddControllerRequestDto.getType())
                .createdDateTime(boardAddControllerRequestDto.getCreatedDateTime())
                .updatedDateTime(boardAddControllerRequestDto.getUpdatedDateTime())
                .category(Category.builder().id(category).build())
                .user(User.builder().id(user).build())
                .build();
    }
}
