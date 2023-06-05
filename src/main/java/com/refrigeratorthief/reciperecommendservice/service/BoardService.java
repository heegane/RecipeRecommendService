package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.comment.CommentRepository;
import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.Category;
import com.refrigeratorthief.reciperecommendservice.domain.board.Board;
import com.refrigeratorthief.reciperecommendservice.domain.board.BoardRepository;
import com.refrigeratorthief.reciperecommendservice.domain.category.CategoryRepository;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.s3.S3Uploader;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;
    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final CommentRepository commentRepository;
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final S3Uploader s3Uploader;

    // 게시글 저장
    @Transactional
    public BoardServiceResponseDto addBoard(BoardAddServiceRequestDto boardAddServiceRequestDto) {
        Category targetCategory = categoryRepository.findById(boardAddServiceRequestDto.getCategory().getId())
                .orElseThrow(()->new CustomException("해당하는 카테고리가 존재하지 않습니다."));
        User targetUser = userRepository.findById(boardAddServiceRequestDto.getUser().getId())
                .orElseThrow(()->new CustomException("해당하는 id가 존재하지 않습니다."));

        Integer categoryId = boardAddServiceRequestDto.getCategory().getId();
        String boardType = boardAddServiceRequestDto.getType();
       
        if(boardType.equals("자유") && (categoryId == 5 || categoryId == 6 || categoryId == 7 || categoryId == 8)) {
            throw new CustomException("해당 카테고리를 고를 수 없습니다. (자유 게시판은 1. 음식자랑, 2. 일상, 3. 질문글, 4. 기타만 가능)");
        } else if (boardType.equals("거래") && (categoryId == 1 || categoryId == 2 || categoryId == 3 || categoryId == 4)) {
            throw new CustomException("해당 카테고리를 고를 수 없습니다. (거래 게시판은 5. 살게요, 6. 팔게요, 7. 나눔해요, 8. 공구해요만 가능)");
        }

        Board targetBoard = boardAddServiceRequestDto.toEntity(targetCategory,targetUser);
        targetBoard.setCreatedDateTime(LocalDateTime.now());
        targetBoard.setUpdatedDateTime(LocalDateTime.now());

        boardRepository.save(targetBoard);

        return new BoardServiceResponseDto(targetBoard);
    }

    // 게시글 상세 정보 조회
    @Transactional(readOnly = true)
    public BoardServiceResponseDto getBoard(Integer id) {

        Board targetBoard = boardRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));

        return new BoardServiceResponseDto(targetBoard);
    }

    // 게시글 수정
    @Transactional
    public BoardUpdateServiceResponseDto updateBoard(BoardUpdateServiceRequestDto boardUpdateServiceRequestDto) {
        Board targetBoard = boardRepository.findById(boardUpdateServiceRequestDto.getId())
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));
        Category targetCategory = categoryRepository.findById(boardUpdateServiceRequestDto.getCategory().getId())
                .orElseThrow(()->new CustomException("해당하는 카테고리가 존재하지 않습니다."));
        User targetUser = userRepository.findById(boardUpdateServiceRequestDto.getUser().getId())
                .orElseThrow(()->new CustomException("해당하는 id가 존재하지 않습니다."));

        if (!Objects.equals(targetBoard.getUser().getId(), boardUpdateServiceRequestDto.getUser().getId())) {
            throw new CustomException("게시글 수정 권한이 없습니다.");
        }
      
        Integer categoryId = boardUpdateServiceRequestDto.getCategory().getId();
        String boardType = boardUpdateServiceRequestDto.getType();

        if(boardType.equals("자유") && (categoryId == 5 || categoryId == 6 || categoryId == 7 || categoryId == 8)) {
            throw new CustomException("해당 카테고리를 고를 수 없습니다. (자유 게시판은 1. 음식자랑, 2. 일상, 3. 질문글, 4. 기타만 가능)");
        } else if (boardType.equals("거래") && (categoryId == 1 || categoryId == 2 || categoryId == 3 || categoryId == 4)) {
            throw new CustomException("해당 카테고리를 고를 수 없습니다. (거래 게시판은 5. 살게요, 6. 팔게요, 7. 나눔해요, 8. 공구해요만 가능)");
        }

        targetBoard.updateBoard(boardUpdateServiceRequestDto.toEntity());
        targetBoard.setUpdatedDateTime(LocalDateTime.now());

        boardRepository.save(targetBoard);
        return new BoardUpdateServiceResponseDto(targetBoard);
    }

    // 게시글 삭제
    @Transactional
    public BoardDeleteServiceResponseDto deleteBoard(Integer id) {

        Board targetBoard = boardRepository.findById(id)
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));

        if(commentRepository.existsByBoard(targetBoard)){
            commentRepository.deleteAllByBoard(targetBoard);
            boardRepository.delete(targetBoard);
        }
        else{
            boardRepository.delete(targetBoard);
        }

        return BoardDeleteServiceResponseDto.builder()
                .id(targetBoard.getId())
                .build();
    }

    // 카테고리별 조회
    @Transactional(readOnly = true)
    public List<BoardServiceResponseDto> findBoardsByCategory(Integer id) {
        List<Board> targetBoards = boardRepository.findBoardsByCategory(id)
                .orElseThrow(()->new CustomException("해당하는 게시글이 존재하지 않습니다."));
        List<BoardServiceResponseDto> dtoList = new ArrayList<>();

        for (Board boards : targetBoards) {
            BoardServiceResponseDto dto = BoardServiceResponseDto.builder()
                    .id(boards.getId())
                    .title(boards.getTitle())
                    .content(boards.getContent())
                    .img(boards.getImg())
                    .type(boards.getType())
                    .createdDateTime(boards.getCreatedDateTime())
                    .updatedDateTime(boards.getUpdatedDateTime())
                    .category(boards.getCategory())
                    .user(boards.getUser())
                    .build();
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public void imgSave(Board board, MultipartFile multipartFile) throws IOException {
        if (multipartFile != null) {
            board.setImg(s3Uploader.uploadFiles(multipartFile, "board"));
        }
    }
}
