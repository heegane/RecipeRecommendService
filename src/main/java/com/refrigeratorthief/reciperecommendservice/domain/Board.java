package com.refrigeratorthief.reciperecommendservice.domain;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "BOARD")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BOARD_IDX")
    private Integer id;

    @Column(name = "BOARD_TITLE", length = 45, nullable = false)
    private String title;

    @Column(name = "BOARD_CONTENT", length = 1000, nullable = false)
    private String content;

    @Column(name = "BOARD_IMG", length = 100)
    private String img;

    @Column(name = "BOARD_TYPE", length = 2, nullable = false)
    private String type;

    @CreatedDate
    @Column(name = "BOARD_CREATE_DTTM", updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "BOARD_UPDATE_DTTM")
    private LocalDateTime updatedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "CATEGORY_CATEGORY_IDX", nullable = false)
    private Category category;

    @ManyToOne(fetch = FetchType.LAZY)
    @Column(name = "USER_USER_ID", length = 45, nullable = false)
    private User user;
}
