package com.refrigeratorthief.reciperecommendservice.domain.comment;

import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Optional<Comment> findById(Integer id);

    @Query(value = "SELECT u FROM comment AS u WHERE u.board.id = ?1")
    Optional<List<Comment>> findCommentsByBoard(Integer id);

    boolean existsByBoard(Board board);
    void deleteAllByBoard(Board board);

}
