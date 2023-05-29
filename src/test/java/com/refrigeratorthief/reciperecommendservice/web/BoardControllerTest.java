package com.refrigeratorthief.reciperecommendservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.service.BoardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@SpringBootTest
class BoardControllerTest {
    @Autowired
    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private BoardService boardService;
    private final TestUtils testUtils = new TestUtils();

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void addBoard() throws Exception {
        //given
        BoardAddControllerRequestDto boardAddControllerRequestDto = BoardAddControllerRequestDto
                .builder()
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type(testUtils.getTestBoard().getType())
                .categoryId(testUtils.getTestBoard().getCategory().getId())
                .userId(testUtils.getTestBoard().getUser().getId())
                .build();

        BoardServiceResponseDto boardServiceResponseDto = BoardServiceResponseDto
                .builder()
                .id(testUtils.getTestBoard().getId())
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type(testUtils.getTestBoard().getType())
                .createdDateTime(testUtils.getTestBoard().getCreatedDateTime())
                .updatedDateTime(testUtils.getTestBoard().getUpdatedDateTime())
                .category(testUtils.getTestBoard().getCategory())
                .user(testUtils.getTestBoard().getUser())
                .build();

        doReturn(boardServiceResponseDto).when(boardService).addBoard(any(BoardAddServiceRequestDto.class));
      
        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.post("/api/v1/board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(boardAddControllerRequestDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("title").description("대상 board의 제목"),
                                fieldWithPath("content").description("대상 board의 내용"),
                                fieldWithPath("img").description("대상 board의 이미지").optional(),
                                fieldWithPath("type").description("대상 board의 type (자유/거래)"),
                                fieldWithPath("category_id").description("대상 board의 카테고리 id(음식자랑,일상,질문글,기타 / 살게요,팔게요,나눔해요,공구해요)"),
                                fieldWithPath("user_id").description("대상 board의 작성자 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("게시 완료된 board의 id (PK)"),
                                fieldWithPath("title").description("게시 완료된 board의 제목"),
                                fieldWithPath("content").description("게시 완료된 board의 내용"),
                                fieldWithPath("img").description("게시 완료된 board의 이미지").optional(),
                                fieldWithPath("type").description("게시 완료된 board의 type"),
                                fieldWithPath("created_date_time").description("게시 완료된 board의 생성시간"),
                                fieldWithPath("updated_date_time").description("게시 완료된 board의 수정시간"),
                                fieldWithPath("category_id").description("게시 완료된 board의 카테고리 id"),
                                fieldWithPath("category_name").description("게시 완료된 board의 카테고리 이름"),
                                fieldWithPath("user_id").description("게시 완료된 board의 작성자 id"),
                                fieldWithPath("user_name").description("게시 완료된 board의 작성자 이름")
                                )
                ));

        //then
        result.andExpect(status().isOk())
            .andExpect(content().json(objectMapper.writeValueAsString(boardServiceResponseDto.toControllerDto())));
        verify(boardService).addBoard(any(BoardAddServiceRequestDto.class));
    }

    @Test
    void findById() throws Exception {
        //given
        Integer id = testUtils.getTestBoard().getId();

        BoardServiceResponseDto boardServiceResponseDto = BoardServiceResponseDto
                .builder()
                .id(id)
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type(testUtils.getTestBoard().getType())
                .createdDateTime(testUtils.getTestBoard().getCreatedDateTime())
                .updatedDateTime(testUtils.getTestBoard().getUpdatedDateTime())
                .category(testUtils.getTestBoard().getCategory())
                .user(testUtils.getTestBoard().getUser())
                .build();

        doReturn(boardServiceResponseDto).when(boardService).getBoard(id);
      
        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.get("/api/v1/board/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("조회 시도할 대상 board의 id (PK)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("조회 완료된 board의 id (PK)"),
                                fieldWithPath("title").description("조회 완료된 board의 제목"),
                                fieldWithPath("content").description("조회 완료된 board의 내용"),
                                fieldWithPath("img").description("조회 완료된 board의 이미지").optional(),
                                fieldWithPath("type").description("조회 완료된 board의 type"),
                                fieldWithPath("created_date_time").description("조회 완료된 board의 생성시간"),
                                fieldWithPath("updated_date_time").description("조회 완료된 board의 수정시간"),
                                fieldWithPath("category_id").description("조회 완료된 board의 카테고리 id"),
                                fieldWithPath("category_name").description("조회 완료된 board의 카테고리 이름"),
                                fieldWithPath("user_id").description("조회 완료된 board의 작성자 id"),
                                fieldWithPath("user_name").description("조회 완료된 board의 작성자 이름")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(boardServiceResponseDto.toControllerDto())));
        verify(boardService).getBoard(id);
    }

    @Test
    void updateBoard() throws Exception {
        //given
        BoardUpdateControllerRequestDto boardUpdateControllerRequestDto = BoardUpdateControllerRequestDto
                .builder()
                .id(testUtils.getTestBoard().getId())
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type(testUtils.getTestBoard().getType())
                .categoryId(testUtils.getTestBoard().getCategory().getId())
                .userId(testUtils.getTestBoard().getUser().getId())
                .build();

        BoardUpdateServiceResponseDto boardUpdateServiceResponseDto = BoardUpdateServiceResponseDto
                .builder()
                .id(testUtils.getTestBoard().getId())
                .title(testUtils.getTestBoard().getTitle())
                .content(testUtils.getTestBoard().getContent())
                .img(testUtils.getTestBoard().getImg())
                .type(testUtils.getTestBoard().getType())
                .createdDateTime(testUtils.getTestBoard().getCreatedDateTime())
                .updatedDateTime(testUtils.getTestBoard().getUpdatedDateTime())
                .categoryId(testUtils.getTestBoard().getCategory().getId())
                .categoryName(testUtils.getTestBoard().getCategory().getName())
                .userId(testUtils.getTestBoard().getUser().getId())
                .userName(testUtils.getTestBoard().getUser().getName())
                .build();

        doReturn(boardUpdateServiceResponseDto).when(boardService).updateBoard(any(BoardUpdateServiceRequestDto.class));

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.put("/api/v1/board")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(boardUpdateControllerRequestDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("대상 board의 id (PK)"),
                                fieldWithPath("title").description("대상 board의 제목"),
                                fieldWithPath("content").description("대상 board의 내용"),
                                fieldWithPath("img").description("대상 board의 이미지").optional(),
                                fieldWithPath("type").description("대상 board의 type (자유/거래)"),
                                fieldWithPath("category_id").description("대상 board의 카테고리 id"),
                                fieldWithPath("user_id").description("대상 board의 작성자 유저 id")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 안내 메시지")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 게시글을 성공적으로 수정했습니다."));
        verify(boardService).updateBoard(any(BoardUpdateServiceRequestDto.class));
    }

    @Test
    void deleteBoard() throws Exception {
        //given
        Integer id = testUtils.getTestBoard().getId();
        BoardDeleteServiceResponseDto boardDeleteServiceResponseDto = BoardDeleteServiceResponseDto
                .builder()
                .id(id)
                .build();
        doReturn(boardDeleteServiceResponseDto).when(boardService).deleteBoard(id);

        //when
        ResultActions result = mvc.perform(
                RestDocumentationRequestBuilders.delete("/api/v1/board/{id}",id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(id))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("board-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("대상 board의 id (PK)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료된 안내 메시지")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 게시글을 성공적으로 삭제했습니다."));
        verify(boardService).deleteBoard(any());

    }

    @Test
    void findBoardsByCategory() throws Exception {
        //given
        Integer categoryId = 1;
        List<BoardServiceResponseDto> targetBoardList = new ArrayList<>();

        BoardServiceResponseDto targetBoardDto1 = new BoardServiceResponseDto(testUtils.getTestBoard());
        targetBoardList.add(targetBoardDto1);

        BoardServiceResponseDto targetBoardDto2 = new BoardServiceResponseDto(testUtils.getTestBoard2());
        targetBoardList.add(targetBoardDto2);

        doReturn(targetBoardList).when(boardService).findBoardsByCategory(categoryId);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/board/category/{categoryId}",categoryId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("boards-get-by-category",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("categoryId").description("조회 시도할 대상 category의 id (PK)")
                        ),
                        responseFields(
                                        fieldWithPath("[].id").description("조회 완료된 board의 id (PK)"),
                                        fieldWithPath("[].title").description("조회 완료된 board의 제목"),
                                        fieldWithPath("[].content").description("조회 완료된 board의 내용"),
                                        fieldWithPath("[].img").description("조회 완료된 board의 이미지").optional(),
                                        fieldWithPath("[].type").description("조회 완료된 board의 type"),
                                        fieldWithPath("[].created_date_time").description("조회 완료된 생성시간"),
                                        fieldWithPath("[].updated_date_time").description("조회 완료된 수정시간"),
                                        fieldWithPath("[].user_id").description("조회 완료된 board의 작성자 id"),
                                        fieldWithPath("[].user_name").description("조회 완료된 board의 작성자 name")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(targetBoardList.stream().map(BoardServiceResponseDto::toCategoryControllerDto).collect(Collectors.toList()))));
        verify(boardService).findBoardsByCategory(categoryId);
    }

}