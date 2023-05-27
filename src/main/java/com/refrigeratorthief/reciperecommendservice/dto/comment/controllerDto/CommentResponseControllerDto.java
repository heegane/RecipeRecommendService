package com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class CommentResponseControllerDto {

    private Integer id;
    private Integer upperId;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String userName;
    private Integer boardId;
}
