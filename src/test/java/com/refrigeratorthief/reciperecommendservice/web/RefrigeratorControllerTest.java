package com.refrigeratorthief.reciperecommendservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.refrigerator.Refrigerator;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.*;
import com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto.UserDeleteRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserDeleteRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.service.RefrigeratorService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
public class RefrigeratorControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private RefrigeratorService refrigeratorService;

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
    void getFridgeAll() throws Exception {
        //given
        String userId = testUtils.getTestUser().getId();
        List<RefrigeratorServiceResponseDto> targetFridgeList = new ArrayList<RefrigeratorServiceResponseDto>();

        RefrigeratorServiceResponseDto targetFridgeDto1 = new RefrigeratorServiceResponseDto(testUtils.getTestRef2());
        targetFridgeList.add(targetFridgeDto1);

        RefrigeratorServiceResponseDto targetFridgeDto2 = new RefrigeratorServiceResponseDto(testUtils.getTestRef3());
        targetFridgeList.add(targetFridgeDto2);

        doReturn(targetFridgeList).when(refrigeratorService).getRefrigeratorAll(userId);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/fridge/{userId}",userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("fridge-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("userId").description("조회할 냉장고의 주인 유저 id")
                        ),
                        responseFields(
                                fieldWithPath("[].id").description("조회된 냉장고-재료 id (PK)"),
                                fieldWithPath("[].ingredientId").description("조회된 냉장고의 재료 id"),
                                fieldWithPath("[].ingredientName").description("조회된 냉장고의 재료 이름"),
                                fieldWithPath("[].ingredientImg").description("조회된 냉장고의 재료 사진").optional(),
                                fieldWithPath("[].ingredientUnitId").description("조회된 냉장고의 재료 단위 id"),
                                fieldWithPath("[].ingredientUnitName").description("조회된 냉장고의 재료 단위 이름"),
                                fieldWithPath("[].quantity").description("조회된 냉장고의 재료 수량"),
                                fieldWithPath("[].expirationDate").description("조회된 냉장고의 재료 소비기한"),
                                fieldWithPath("[].location").description("조회된 냉장고의 재료 저장 장소 (냉장/냉동/실온)"),
                                fieldWithPath("[].userId").description("해당 냉장고의 유저 id"),
                                fieldWithPath("[].userName").description("해당 냉장고의 유저 닉네임")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(targetFridgeList.stream()
                                .map(RefrigeratorServiceResponseDto::toControllerDto)
                                .collect(Collectors.toList()))));
        verify(refrigeratorService).getRefrigeratorAll(userId);
    }

    @Test
    void getFridge() throws Exception {
        //given
        Integer id = testUtils.getTestRef().getId();
        RefrigeratorServiceResponseDto refrigeratorServiceResponseDto = new RefrigeratorServiceResponseDto(testUtils.getTestRef());

        doReturn(refrigeratorServiceResponseDto).when(refrigeratorService).getRefrigerator(id);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/fridge/ingredient/{id}",id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("fridge-get",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("조회할 냉장고-재료 id (PK)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("조회된 냉장고-재료 id (PK)"),
                                fieldWithPath("ingredientId").description("조회된 냉장고의 재료 id"),
                                fieldWithPath("ingredientName").description("조회된 냉장고의 재료 이름"),
                                fieldWithPath("ingredientImg").description("조회된 냉장고의 재료 사진").optional(),
                                fieldWithPath("ingredientUnitId").description("조회된 냉장고의 재료 단위 id"),
                                fieldWithPath("ingredientUnitName").description("조회된 냉장고의 재료 단위 이름"),
                                fieldWithPath("quantity").description("조회된 냉장고의 재료 수량"),
                                fieldWithPath("expirationDate").description("조회된 냉장고의 재료 소비기한"),
                                fieldWithPath("location").description("조회된 냉장고의 재료 저장 장소 (냉장/냉동/실온)"),
                                fieldWithPath("userId").description("해당 냉장고의 유저 id"),
                                fieldWithPath("userName").description("해당 냉장고의 유저 닉네임")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(refrigeratorServiceResponseDto.toControllerDto())));
        verify(refrigeratorService).getRefrigerator(id);
    }

    @Test
    void addFridge() throws Exception {
        //given
        RefrigeratorAddControllerRequestDto refrigeratorAddControllerRequestDto = RefrigeratorAddControllerRequestDto.builder()
                .expirationDate(testUtils.getTestRef().getExpirationDate())
                .quantity(testUtils.getTestRef().getQuantity())
                .location(testUtils.getTestRef().getLocation())
                .userId(testUtils.getTestUser().getId())
                .ingredientId(testUtils.getTestIngredient().getId())
                .build();

        RefrigeratorAddServiceResponseDto refrigeratorAddServiceResponseDto = RefrigeratorAddServiceResponseDto.builder()
                .id(testUtils.getTestRef().getId())
                .expirationDate(testUtils.getTestRef().getExpirationDate())
                .quantity(testUtils.getTestRef().getQuantity())
                .location(testUtils.getTestRef().getLocation())
                .userId(testUtils.getTestRef().getUser().getId())
                .userName(testUtils.getTestRef().getUser().getName())
                .ingredientId(testUtils.getTestRef().getIngredient().getId())
                .ingredientName(testUtils.getTestRef().getIngredient().getName())
                .ingredientImg(testUtils.getTestRef().getIngredient().getImg())
                .ingredientUnitId(testUtils.getTestRef().getIngredient().getUnit().getId())
                .ingredientUnitName(testUtils.getTestRef().getIngredient().getUnit().getName())
                .build();

        doReturn(refrigeratorAddServiceResponseDto).when(refrigeratorService).addFridge(any(RefrigeratorAddServiceRequestDto.class));

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/fridge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refrigeratorAddControllerRequestDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("fridge-add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").description("해당 냉장고의 유저 id"),
                                fieldWithPath("ingredientId").description("해당 냉장고에 추가할 재료의 id"),
                                fieldWithPath("expirationDate").description("해당 냉장고에 추가할 재료의 소비기한"),
                                fieldWithPath("quantity").description("해당 냉장고에 추가할 재료의 수량"),
                                fieldWithPath("location").description("해당 냉장고에 추가할 재료 저장 장소 (냉장/냉동/실온)")
                        ),
                        responseFields(
                                fieldWithPath("id").description("추가된 냉장고-재료 id (PK)"),
                                fieldWithPath("ingredientId").description("냉장고에 추가된 재료의 id"),
                                fieldWithPath("ingredientName").description("냉장고에 추가된 재료의 이름"),
                                fieldWithPath("ingredientImg").description("냉장고에 추가된 재료의 사진").optional(),
                                fieldWithPath("ingredientUnitId").description("냉장고에 추가된 재료의 단위 id"),
                                fieldWithPath("ingredientUnitName").description("냉장고에 추가된 재료의 단위 이름"),
                                fieldWithPath("quantity").description("냉장고에 추가된 재료의 수량"),
                                fieldWithPath("expirationDate").description("냉장고에 추가된 재료의 소비기한"),
                                fieldWithPath("location").description("냉장고에 추가된 재료의 저장 장소 (냉장/냉동/실온)"),
                                fieldWithPath("userId").description("해당 냉장고의 유저 id"),
                                fieldWithPath("userName").description("해당 냉장고의 유저 닉네임")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(refrigeratorAddServiceResponseDto.toControllerDto(refrigeratorAddServiceResponseDto))));
        verify(refrigeratorService).addFridge(any(RefrigeratorAddServiceRequestDto.class));
    }

    @Test
    void deleteFridge() throws Exception {
        //given
        Integer id = testUtils.getTestRef().getId();
        RefrigeratorDeleteServiceResponseDto refrigeratorDeleteServiceResponseDto = RefrigeratorDeleteServiceResponseDto
                .builder()
                .id(id)
                .build();

        doReturn(refrigeratorDeleteServiceResponseDto).when(refrigeratorService).deleteFridge(id);

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.delete("/api/v1/fridge/{id}",id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("fridge-delete",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("id").description("삭제할 냉장고-재료의 id (PK)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("삭제 완료 안내 메시지")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("냉장고에서 해당 재료를 성공적으로 삭제했습니다."));
        verify(refrigeratorService).deleteFridge(id);
    }

    @Test
    void updateFridge() throws Exception {
        //given
        RefrigeratorUpdateControllerRequestDto refrigeratorUpdateControllerRequestDto = RefrigeratorUpdateControllerRequestDto.builder()
                .userId(testUtils.getTestRef().getUser().getId())
                .ingredientId(testUtils.getTestRef().getIngredient().getId())
                .expirationDate(testUtils.getTestRef().getExpirationDate())
                .quantity(testUtils.getTestRef().getQuantity())
                .location(testUtils.getTestRef().getLocation())
                .build();

        RefrigeratorUpdateServiceResponseDto refrigeratorUpdateServiceResponseDto = RefrigeratorUpdateServiceResponseDto.builder()
                .id(testUtils.getTestRef().getId())
                .expirationDate(testUtils.getTestRef().getExpirationDate())
                .quantity(testUtils.getTestRef().getQuantity())
                .location(testUtils.getTestRef().getLocation())
                .userId(testUtils.getTestRef().getUser().getId())
                .userName(testUtils.getTestRef().getUser().getName())
                .ingredientId(testUtils.getTestRef().getIngredient().getId())
                .ingredientName(testUtils.getTestRef().getIngredient().getName())
                .ingredientImg(testUtils.getTestRef().getIngredient().getImg())
                .ingredientUnitId(testUtils.getTestRef().getIngredient().getUnit().getId())
                .ingredientUnitName(testUtils.getTestRef().getIngredient().getUnit().getName())
                .build();

        doReturn(refrigeratorUpdateServiceResponseDto).when(refrigeratorService).updateFridge(any(RefrigeratorUpdateServiceRequestDto.class));

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.put("/api/v1/fridge")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(refrigeratorUpdateControllerRequestDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("fridge-update",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("userId").description("해당 냉장고의 유저 id"),
                                fieldWithPath("ingredientId").description("해당 재료의 id"),
                                fieldWithPath("expirationDate").description("수정할 해당 재료의 소비기한"),
                                fieldWithPath("quantity").description("수정할 해당 재료의 수량"),
                                fieldWithPath("location").description("수정할 해당 재료의 저장 장소 (냉장/냉동/실온)")
                        ),
                        responseFields(
                                fieldWithPath("message").description("수정 완료 안내 메시지")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("해당 냉장고 재료 정보를 성공적으로 수정했습니다."));
        verify(refrigeratorService).updateFridge(any(RefrigeratorUpdateServiceRequestDto.class));
    }

}
