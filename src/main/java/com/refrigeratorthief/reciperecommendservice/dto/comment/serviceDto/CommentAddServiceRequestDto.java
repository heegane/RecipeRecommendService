package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentAddServiceRequestDto {
    private Integer upperId;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String user;
    private Integer board;
}
