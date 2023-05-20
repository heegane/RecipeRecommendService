package com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto;

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
    private String category;
    private String user;
}
