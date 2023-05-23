package com.refrigeratorthief.reciperecommendservice.domain.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<Board, Integer> {
    Optional<Board> findById(Integer id);

    @Query(value = "SELECT u FROM BOARD AS u WHERE u.category.id = ?1")
    Optional<List<Board>> findBoardsByCategory(Integer id);
}
