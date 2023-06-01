package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserDeleteRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserLoginRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserRegisterRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@RequiredArgsConstructor
@Service
public class UserService {

    private final int batchSize = 100;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final UserRepository userRepository;

    // 유저 조회
    @Transactional(readOnly = true)
    public UserResponseServiceDto findById(String id) {
        User targetUser = userRepository.findUserById(id)
                .orElseThrow(()->new CustomException("해당하는 id가 없습니다. id를 다시 확인해주세요!"));

        return new UserResponseServiceDto(targetUser);
    }

    // 유저 닉네임 조회
    @Transactional(readOnly = true)
    public UserResponseServiceDto findUserByName(String name) {
        User targetUser = userRepository.findUserByName(name)
                .orElseThrow(()->new CustomException("해당하는 id가 없습니다. name을 다시 확인해주세요!"));

        return new UserResponseServiceDto(targetUser);
    }

    // 유저 회원가입
    @Transactional
    public String register(UserRegisterRequestServiceDto userRegisterRequestServiceDto){

        if(userRepository.existsUserById(userRegisterRequestServiceDto.getId())) {
            throw new CustomException("동일한 id가 존재합니다!");
        }

        if(userRepository.existsUserByName(userRegisterRequestServiceDto.getName())) {
            throw new CustomException("동일한 닉네임이 존재합니다!");
        }

        if(!Objects.equals(userRegisterRequestServiceDto.getPw(), userRegisterRequestServiceDto.getCheckPw())) {
            throw new CustomException("비밀번호를 다시 확인해주세요!");
        }

        return userRepository.save(userRegisterRequestServiceDto.toEntity()).getName();
    }

    // 유저 로그인
    @Transactional(readOnly = true)
    public String login(UserLoginRequestServiceDto userLoginRequestServiceDto){

        User targetUser = userRepository.findUserById(userLoginRequestServiceDto.getId())
                .orElseThrow(()->new CustomException("해당하는 id가 없습니다. id를 다시 확인해주세요!"));

        if(!Objects.equals(targetUser.getPw(), userLoginRequestServiceDto.getPw())){
            throw new CustomException("비밀번호가 일치하지 않습니다. 비밀번호를 다시 확인해주세요!");
        }

        return targetUser.getName();
    }

    // 유저 삭제
    @Transactional
    public String delete(UserDeleteRequestServiceDto userDeleteRequestServiceDto){

        User targetUser = userRepository.findUserById(userDeleteRequestServiceDto.getId())
                .orElseThrow(()->new CustomException("해당하는 id가 없습니다. id를 다시 확인해주세요!"));

        if(!Objects.equals(userDeleteRequestServiceDto.getPw(), userDeleteRequestServiceDto.getCheckPw())) {
            throw new CustomException("비밀번호를 다시 확인해주세요!");
        }

        if(!Objects.equals(targetUser.getPw(), userDeleteRequestServiceDto.getPw())){
            throw new CustomException("비밀번호가 일치하지 않습니다. 비밀번호를 다시 확인해주세요!");
        }

        userRepository.delete(targetUser);
        return targetUser.getId();
    }
}
