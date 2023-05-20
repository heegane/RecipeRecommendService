package com.refrigeratorthief.reciperecommendservice.domain.comment;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
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
@Entity(name = "COMMENT")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "COMMENT_IDX")
    private Integer id;

    @Column(name = "COMMENT_UPPER")
    private Integer upperId;

    @Column(name = "COMMENT_CONTENT", length = 100, nullable = false)
    private String content;

    @CreatedDate
    @Column(name = "COMMENT_CREATE_DTTM", updatable = false)
    private LocalDateTime createdDateTime;

    @LastModifiedDate
    @Column(name = "COMMENT_UPDATE_DTTM")
    private LocalDateTime updatedDateTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_USER_ID", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_BOARD_IDX",nullable = false)
    private Board board;
}
