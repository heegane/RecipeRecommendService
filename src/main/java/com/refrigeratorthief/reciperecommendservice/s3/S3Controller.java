package com.refrigeratorthief.reciperecommendservice.s3;

import com.refrigeratorthief.reciperecommendservice.domain.ingredient.Ingredient;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RequiredArgsConstructor
@RequestMapping("/api/v1/upload")
@RestController
@Validated
public class S3Controller {
    private final S3Uploader s3Uploader;

    @PostMapping()
    public ResponseEntity<MessageDto> updateUserImage(@RequestPart MultipartFile multipartFile) throws IOException {
        s3Uploader.uploadFiles(multipartFile, "ingredient");
        return ResponseEntity.ok(new MessageDto("이미지를 성공적으로 저장했습니다."));
    }
}