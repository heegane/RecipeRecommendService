package com.refrigeratorthief.reciperecommendservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto.UserDeleteRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto.UserLoginRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto.UserRegisterRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserDeleteRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserLoginRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserRegisterRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.service.UserService;
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


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@SpringBootTest
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private UserService userService;

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
    void getUser() throws Exception {
        //given
        String userId = "test";

        UserResponseServiceDto userResponseServiceDto = UserResponseServiceDto.builder()
                .id(userId)
                .name("테스터")
                .city(testUtils.getTestUser().getCity())
                .dong(testUtils.getTestUser().getDong())
                .build();

        doReturn(userResponseServiceDto).when(userService).findById(userId);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/user/{userId}",userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("조회 시도할 대상 user의 id (PK)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("조회 완료된 user의 id (PK)"),
                                fieldWithPath("name").description("조회 완료된 user의 닉네임"),
                                fieldWithPath("city").description("조회 완료된 user의 주소 (도/시)"),
                                fieldWithPath("dong").description("조회 완료된 user의 주소 (동)")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(userResponseServiceDto.toControllerDto())));
        verify(userService).findById(userId);
    }

    @Test
    void register() throws Exception {
        //given
        String userName = "테스터";
        UserRegisterRequestControllerDto userRegisterRequestControllerDto = UserRegisterRequestControllerDto.builder()
                .id("test")
                .name("테스터")
                .pw(testUtils.getTestUser().getPw())
                .checkPw(testUtils.getTestUser().getPw())
                .city(testUtils.getTestUser().getCity())
                .dong(testUtils.getTestUser().getDong())
                .build();

        doReturn(userName).when(userService).register(userRegisterRequestControllerDto.toServiceDto());

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/user/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRegisterRequestControllerDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user-register",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("대상 user의 id (PK)"),
                                fieldWithPath("name").description("대상 user의 닉네임"),
                                fieldWithPath("pw").description("대상 user의 비밀번호"),
                                fieldWithPath("check_pw").description("대상 user의 확인 비밀번호"),
                                fieldWithPath("city").description("대상 user의 주소(도/시)"),
                                fieldWithPath("dong").description("대상 user의 주소(동)")
                        ),
                        responseFields(
                                fieldWithPath("name").description("가입 완료된 user의 닉네임")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userRegisterRequestControllerDto.getName()));
        verify(userService).register(any(UserRegisterRequestServiceDto.class));
    }

    @Test
    void login() throws Exception {
        //given
        UserLoginRequestControllerDto userLoginRequestControllerDto = UserLoginRequestControllerDto.builder()
                .id("test")
                .pw("test")
                .build();
        String userName = "테스터";

        doReturn(userName).when(userService).login(userLoginRequestControllerDto.toServiceDto());

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/user/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userLoginRequestControllerDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user-login",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("대상 user의 id (PK)"),
                                fieldWithPath("pw").description("대상 user의 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("name").description("로그인 완료된 user의 닉네임")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(userName));
        verify(userService).login(any(UserLoginRequestServiceDto.class));
    }

    @Test
    void delete() throws Exception {
        //given
        UserDeleteRequestControllerDto userDeleteRequestControllerDto = UserDeleteRequestControllerDto.builder()
                .id("test")
                .pw("test")
                .checkPw("test")
                .build();

        String userId = "test";

        doReturn(userId).when(userService).delete(userDeleteRequestControllerDto.toServiceDto());

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/v1/user")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userDeleteRequestControllerDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("user-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("id").description("대상 user의 id (PK)"),
                                fieldWithPath("pw").description("대상 user의 비밀번호"),
                                fieldWithPath("check_pw").description("대상 user의 확인 비밀번호")
                        ),
                        responseFields(
                                fieldWithPath("id").description("삭제 완료된 user의 id (PK)")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userId));
        verify(userService).delete(any(UserDeleteRequestServiceDto.class));
    }

}
