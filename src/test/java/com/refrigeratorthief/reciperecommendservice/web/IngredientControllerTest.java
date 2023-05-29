package com.refrigeratorthief.reciperecommendservice.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.controllerDto.IngredientAddRequestControllerDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientAddRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.Ingredient.serviceDto.IngredientResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.service.IngredientService;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@ActiveProfiles("test")
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
@SpringBootTest
public class IngredientControllerTest {

    @Autowired
    private MockMvc mvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockBean
    private IngredientService ingredientService;

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
    void getIngredientAll() throws Exception {

        //given
        List<IngredientResponseServiceDto> targetIngredientList = new ArrayList<>();

        IngredientResponseServiceDto targetIngredientDto1 = new IngredientResponseServiceDto(testUtils.getTestIngredient());
        targetIngredientList.add(targetIngredientDto1);

        IngredientResponseServiceDto targetIngredientDto2 = new IngredientResponseServiceDto(testUtils.getTestIngredient2());
        targetIngredientList.add(targetIngredientDto2);

        doReturn(targetIngredientList).when(ingredientService).getIngredientAll();

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.get("/api/v1/ingredient")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("ingredient-get-all",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        responseFields(
                                fieldWithPath("[].id").description("조회된 재료 id (PK)"),
                                fieldWithPath("[].name").description("조회된 재료 이름"),
                                fieldWithPath("[].img").description("조회된 재료 사진 경로").optional()
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(
                        objectMapper.writeValueAsString(targetIngredientList.stream()
                                .map(IngredientResponseServiceDto::toTinyControllerDto)
                                .collect(Collectors.toList()))));
        verify(ingredientService).getIngredientAll();
    }

    @Test
    void addIngredient() throws Exception {

        //given
        IngredientAddRequestControllerDto ingredientAddRequestControllerDto = IngredientAddRequestControllerDto.builder()
                .name(testUtils.getTestIngredient().getName())
                .img(testUtils.getTestIngredient().getImg())
                .ingredientUnitId(testUtils.getTestIngredient().getUnit().getId())
                .build();

        IngredientResponseServiceDto ingredientResponseServiceDto = IngredientResponseServiceDto.builder()
                .id(testUtils.getTestIngredient().getId())
                .name(testUtils.getTestIngredient().getName())
                .img(testUtils.getTestIngredient().getImg())
                .ingredientUnit(testUtils.getTestIngredientUnit())
                .build();

        doReturn(ingredientResponseServiceDto).when(ingredientService).addIngredient(any(IngredientAddRequestServiceDto.class));

        //when
        ResultActions result = mvc.perform(
                        RestDocumentationRequestBuilders.post("/api/v1/ingredient")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(ingredientAddRequestControllerDto))
                                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andDo(document("ingredient-add",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        requestFields(
                                fieldWithPath("name").description("직접 추가할 재료의 이름"),
                                fieldWithPath("img").description("직접 추가할 재료의 사진 경로").optional(),
                                fieldWithPath("ingredient_unit_id").description("직접 추가할 재료의 단위")
                        ),
                        responseFields(
                                fieldWithPath("id").description("추가 완료된 재료의 id (PK)"),
                                fieldWithPath("name").description("추가 완료된 재료의 이름"),
                                fieldWithPath("img").description("추가 완료된 재료의 사진 경로").optional(),
                                fieldWithPath("ingredient_unit_id").description("추가 완료된 재료의 단위 id"),
                                fieldWithPath("ingredient_unit_name").description("추가 완료된 재료의 단위 이름")
                        )
                ));

        //then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(ingredientResponseServiceDto.toControllerDto())));
        verify(ingredientService).addIngredient(any(IngredientAddRequestServiceDto.class));
    }
}
