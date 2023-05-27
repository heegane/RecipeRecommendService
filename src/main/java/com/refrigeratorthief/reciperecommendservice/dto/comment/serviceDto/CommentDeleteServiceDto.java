package com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentDeleteServiceDto {
    private Integer id;
    private String user;
    private Integer board;
}
