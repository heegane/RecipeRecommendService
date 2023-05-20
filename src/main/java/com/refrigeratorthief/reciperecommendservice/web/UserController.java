package com.refrigeratorthief.reciperecommendservice.web;

import com.refrigeratorthief.reciperecommendservice.dto.user.controllerDto.*;
import com.refrigeratorthief.reciperecommendservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Validated
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    // 유저 조회
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponseControllerDto> getUser(
            @PathVariable String userId){

        return ResponseEntity.ok(userService.findById(userId).toControllerDto());
    }

    // 유저 회원가입
    @PostMapping("/register")
    public ResponseEntity<UserNameResponseControllerDto> registerUser(
            @RequestBody UserRegisterRequestControllerDto userRegisterRequestControllerDto){

        String targetName = userService.register(userRegisterRequestControllerDto.toServiceDto());

        return ResponseEntity.ok(new UserNameResponseControllerDto(targetName));
    }

    // TODO GetMapping인지 PostMapping인지 확인
    // 유저 로그인
    @PostMapping("/login")
    public ResponseEntity<UserNameResponseControllerDto> loginUser(
            @RequestBody UserLoginRequestControllerDto userLoginRequestControllerDto){

        String targetName = userService.login(userLoginRequestControllerDto.toServiceDto());
        return ResponseEntity.ok(new UserNameResponseControllerDto(targetName));
    }


    // 유저 삭제
    @DeleteMapping()
    public ResponseEntity<UserIdResponseControllerDto> deleteUser(
            @RequestBody UserDeleteRequestControllerDto userDeleteRequestControllerDto){

        String targetId = userService.delete(userDeleteRequestControllerDto.toServiceDto());

        return ResponseEntity.ok(new UserIdResponseControllerDto(targetId));
    }
}
