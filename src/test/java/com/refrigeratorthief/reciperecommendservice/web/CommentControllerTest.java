package com.refrigeratorthief.reciperecommendservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.controllerDto.CommentUpdateRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentAddServiceRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.comment.serviceDto.CommentUpdateServiceRequestDto;
import com.refrigeratorthief.reciperecommendservice.service.BoardService;
import com.refrigeratorthief.reciperecommendservice.service.CommentService;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@SpringBootTest
class CommentControllerTest {

    @Autowired
    private MockMvc mvc;
    private final ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private CommentService commentService;
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
    void addComment() throws Exception {
        //given
        CommentAddControllerRequestDto commentAddControllerRequestDto = CommentAddControllerRequestDto
                .builder()
                .upperId(testUtils.getTestComment().getUpperId())
                .content(testUtils.getTestComment().getContent())
                .userId(testUtils.getTestComment().getUser().getId())
                .boardId(testUtils.getTestComment().getBoard().getId())
                .build();

        CommentServiceResponseDto commentServiceResponseDto = CommentServiceResponseDto
                .builder()
                .id(testUtils.getTestComment().getId())
                .upperId(testUtils.getTestComment().getUpperId())
                .content(testUtils.getTestComment().getContent())
                .createdDateTime(testUtils.getTestComment().getCreatedDateTime())
                .updatedDateTime(testUtils.getTestComment().getUpdatedDateTime())
                .userName(testUtils.getTestComment().getUser().getName())
                .boardId(testUtils.getTestComment().getBoard().getId())
                .build();

        doReturn(commentServiceResponseDto).when(commentService).addComment((any(CommentAddServiceRequestDto.class)));

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentAddControllerRequestDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("comment-post",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("upper_id").description("대상 comment의 상위 comment id"),
                                fieldWithPath("content").description("대상 comment의 내용"),
                                fieldWithPath("user").description("대상 comment의 작성자 id"),
                                fieldWithPath("board").description("대상 comment의 게시글 id")
                        ),
                        responseFields(
                                fieldWithPath("id").description("등록 완료된 comment의 id (PK)"),
                                fieldWithPath("upper_id").description("등록 완료된 comment의 상위 comment id"),
                                fieldWithPath("content").description("등록 완료된 comment의 내용"),
                                fieldWithPath("created_date_time").description("등록 완료된 comment의 생성시간"),
                                fieldWithPath("updated_date_time").description("등록 완료된 comment의 수정시간"),
                                fieldWithPath("user_name").description("등록 완료된 comment의 작성자의 닉네임"),
                                fieldWithPath("board_id").description("등록 완료된 comment의 게시글 id")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(commentServiceResponseDto.toControllerDto())));
        verify(commentService).addComment(any(CommentAddServiceRequestDto.class));
    }

    @Test
    void updateComment() throws Exception {
        //given
        CommentUpdateRequestControllerDto commentUpdateRequestControllerDto = CommentUpdateRequestControllerDto
                .builder()
                .id(testUtils.getTestComment().getId())
                .content(testUtils.getTestComment().getContent())
                .userId(testUtils.getTestComment().getUser().getId())
                .build();

        doNothing().when(commentService).updateComment(commentUpdateRequestControllerDto.toServiceDto());

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.put("/api/v1/comment")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(commentUpdateRequestControllerDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("comment-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("대상 comment의 id (PK)"),
                                fieldWithPath("content").description("대상 comment의 내용"),
                                fieldWithPath("user_id").description("대상 comment의 작성자 유저 id")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 안내 메시지")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 댓글을 성공적으로 수정했습니다."));
        verify(commentService).updateComment(any(CommentUpdateServiceRequestDto.class));
    }

    @Test
    void deleteComment() throws Exception {
        //given
        Integer id = testUtils.getTestComment().getId();

        doNothing().when(commentService).deleteComment(id);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/v1/comment/{id}",id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(id))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("comment-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("대상 comment의 id (PK)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료된 안내 메시지")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 댓글을 성공적으로 삭제했습니다."));
        verify(commentService).deleteComment(any());
    }

    @Test
    void findCommentsByBoard() throws Exception {
        //given
        Integer boardId = 2;
        List<CommentServiceResponseDto> targetCommentList = new ArrayList<>();

        CommentServiceResponseDto targetCommentDto1 = new CommentServiceResponseDto(testUtils.getTestComment());
        targetCommentList.add(targetCommentDto1);
        CommentServiceResponseDto targetCommentDto2 = new CommentServiceResponseDto(testUtils.getTestComment2());
        targetCommentList.add(targetCommentDto2);

        doReturn(targetCommentList).when(commentService).findCommentsByBoard(boardId);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/comment/{boardId}",boardId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("comment-get-by-board",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("boardId").description("조회할 게시글의 id")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("조회된 comment의 id (PK)"),
                                fieldWithPath("[].upper_id").description("조회된 comment의 상위 comment id"),
                                fieldWithPath("[].content").description("조회된 comment의 내용"),
                                fieldWithPath("[].created_date_time").description("조회된 comment의 생성시간"),
                                fieldWithPath("[].updated_date_time").description("조회된 comment의 수정시간"),
                                fieldWithPath("[].user_name").description("해당 comment의 유저 닉네임"),
                                fieldWithPath("[].board_id").description("해당 comment의 게시글 id")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(targetCommentList.stream().map(CommentServiceResponseDto::toControllerDto).collect(Collectors.toList()))));
        verify(commentService).findCommentsByBoard(boardId);
    }
}