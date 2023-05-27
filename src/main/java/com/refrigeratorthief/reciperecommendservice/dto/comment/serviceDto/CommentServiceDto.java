package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentServiceDto {
    private Integer id;
    private Integer upperId;
    private String content;
    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String user;
    private Integer board;
}
