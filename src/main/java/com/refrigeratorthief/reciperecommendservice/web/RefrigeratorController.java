package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.dto.board.controllerDto.BoardUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.board.serviceDto.BoardUpdateServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.general.MessageDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorAddControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorAddControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorControllerResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.controllerDto.RefrigeratorUpdateControllerRequestDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorAddServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorDeleteServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.dto.refrigerator.serviceDto.RefrigeratorUpdateServiceResponseDto;
import com.refrigeratorthief.reciperecommendservice.service.RefrigeratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/v1/fridge")
@RestController
public class RefrigeratorController {

    private final RefrigeratorService refrigeratorService;

    // 냉장고 재료 조회
    @GetMapping("/{id}")
    public ResponseEntity<RefrigeratorControllerResponseDto> findById(@PathVariable Integer id) {
        RefrigeratorServiceResponseDto refrigeratorServiceResponseDto = refrigeratorService.getRefrigerator(id);
        return ResponseEntity.ok(refrigeratorServiceResponseDto.toControllerDto(refrigeratorServiceResponseDto));
    }

    // 냉장고 재료 추가
    @PostMapping()
    public ResponseEntity<RefrigeratorAddControllerResponseDto> addFridge(@RequestBody RefrigeratorAddControllerRequestDto refrigeratorAddControllerRequestDto) {
        RefrigeratorAddServiceResponseDto refrigeratorAddServiceResponseDto = refrigeratorService.addFridge(refrigeratorAddControllerRequestDto.toServiceDto(refrigeratorAddControllerRequestDto));
        return ResponseEntity.ok(refrigeratorAddServiceResponseDto.toControllerDto(refrigeratorAddServiceResponseDto));
    }

    // 냉장고 재료 수정
    @PutMapping()
    public ResponseEntity<MessageDto> updateFridge(@RequestBody RefrigeratorUpdateControllerRequestDto refrigeratorUpdateControllerRequestDto) {
        refrigeratorService.updateFridge(refrigeratorUpdateControllerRequestDto.toServiceDto(refrigeratorUpdateControllerRequestDto));
        return ResponseEntity.ok(new MessageDto("해당 냉장고 재료 정보를 성공적으로 수정했습니다."));
    }

    // 냉장고 재료 삭제
     @DeleteMapping("/{id}")
     public ResponseEntity<MessageDto> deleteFridge(@PathVariable Integer id) {
        refrigeratorService.deleteFridge(id);
        return ResponseEntity.ok(new MessageDto("냉장고에서 해당 재료를 성공적으로 삭제했습니다."));
     }
}
