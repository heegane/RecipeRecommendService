package com.refrigeratorthief.reciperecommendservice.service;

import com.refrigeratorthief.reciperecommendservice.TestUtils;
import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import com.refrigeratorthief.reciperecommendservice.domain.user.UserRepository;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserDeleteRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserLoginRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserRegisterRequestServiceDto;
import com.refrigeratorthief.reciperecommendservice.dto.user.serviceDto.UserResponseServiceDto;
import com.refrigeratorthief.reciperecommendservice.utils.CustomException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;

@ComponentScan
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    private final TestUtils testUtils = new TestUtils();

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown(){

    }

    @Test
    void findById() {

        //given
        String testId = "erica";
        User testEntity = User.builder()
                .id(testId)
                .name("하냥이")
                .pw("erica")
                .city("경기도 안산시")
                .dong("상록구")
                .build();

        doReturn(Optional.of(testEntity)).when(userRepository).findUserById(testId);

        //when
        UserResponseServiceDto result = userService.findById(testId);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.toString(), Objects.requireNonNull(result).toString());
        verify(userRepository).findUserById(testId);
    }

    @Test
    void register() {

        //given
        UserRegisterRequestServiceDto userRegisterRequestServiceDto = UserRegisterRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .name(testUtils.getTestUser().getName())
                .pw(testUtils.getTestUser().getPw())
                .checkPw(testUtils.getTestUser().getPw())
                .city(testUtils.getTestUser().getCity())
                .dong(testUtils.getTestUser().getDong())
                .build();

        User testEntity = userRegisterRequestServiceDto.toEntity();
        testEntity.setId(testUtils.getTestUser().getId());

        doReturn(testEntity).when(userRepository).save(any());

        //when
        String resultId = userService.register(userRegisterRequestServiceDto);

        //then
        Assertions.assertNotNull(resultId);
        Assertions.assertEquals(resultId, Objects.requireNonNull(resultId));
        verify(userRepository).save(any());

    }

    @Test
    void login() {

        //given
        String testId = testUtils.getTestUser().getId();
        String testName = testUtils.getTestUser().getName();
        UserLoginRequestServiceDto userLoginRequestServiceDto = UserLoginRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .pw(testUtils.getTestUser().getPw())
                .build();

        User testEntity = userLoginRequestServiceDto.toEntity();
        testEntity.setName(testUtils.getTestUser().getName());
        testEntity.setCity(testUtils.getTestUser().getCity());
        testEntity.setDong(testUtils.getTestUser().getDong());

        doReturn(Optional.of(testEntity)).when(userRepository).findUserById(testId);

        //when
        String resultName = userService.login(userLoginRequestServiceDto);

        //then
        Assertions.assertNotNull(resultName);
        Assertions.assertEquals(testName, Objects.requireNonNull(resultName));
        verify(userRepository).findUserById(testId);

    }

    @Test
    void delete() {

        //given
        String testId = testUtils.getTestUser().getId();
        UserDeleteRequestServiceDto userDeleteRequestServiceDto = UserDeleteRequestServiceDto
                .builder()
                .id(testId)
                .pw(testUtils.getTestUser().getPw())
                .checkPw(testUtils.getTestUser().getPw())
                .build();

        User testEntity = testUtils.getTestUser();

        doReturn(Optional.of(testEntity)).when(userRepository).findUserById(testId);

        //when
        String resultId = userService.delete(userDeleteRequestServiceDto);

        //then
        Assertions.assertNotNull(resultId);
        Assertions.assertEquals(testId, Objects.requireNonNull(resultId));
        verify(userRepository).findUserById(testId);
        verify(userRepository).delete(testEntity);

    }

    @Test
    void duplicatedIdErrorWhenRegister() {

        //given
        String testId = testUtils.getTestUser().getId();
        UserRegisterRequestServiceDto userRegisterRequestServiceDto = UserRegisterRequestServiceDto
                .builder()
                .id(testId)
                .name(testUtils.getTestUser().getName())
                .pw(testUtils.getTestUser().getPw())
                .checkPw(testUtils.getTestUser().getPw())
                .city(testUtils.getTestUser().getCity())
                .dong(testUtils.getTestUser().getDong())
                .build();

        doReturn(true).when(userRepository).existsUserById(testId);

        //when
        CustomException result = assertThrows(CustomException.class, ()-> userService.register(userRegisterRequestServiceDto));

        // then
        assertEquals("동일한 id가 존재합니다!",result.getMessage());
    }

    @Test
    void duplicatedNameErrorWhenRegister() {

        //given
        String testName = testUtils.getTestUser().getName();
        UserRegisterRequestServiceDto userRegisterRequestServiceDto = UserRegisterRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .name(testName)
                .pw(testUtils.getTestUser().getPw())
                .checkPw(testUtils.getTestUser().getPw())
                .city(testUtils.getTestUser().getCity())
                .dong(testUtils.getTestUser().getDong())
                .build();

        doReturn(true).when(userRepository).existsUserByName(testName);

        //when
        CustomException result = assertThrows(CustomException.class, ()-> userService.register(userRegisterRequestServiceDto));

        // then
        assertEquals("동일한 닉네임이 존재합니다!",result.getMessage());
    }

    @Test
    void mismatchedPasswordsErrorWhenRegister() {

        //given
        UserRegisterRequestServiceDto userRegisterRequestServiceDto = UserRegisterRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .name(testUtils.getTestUser().getName())
                .pw(testUtils.getTestUser().getPw())
                .checkPw("erico")
                .city(testUtils.getTestUser().getCity())
                .dong(testUtils.getTestUser().getDong())
                .build();

        //when
        CustomException result = assertThrows(CustomException.class, ()-> userService.register(userRegisterRequestServiceDto));

        //then
        assertEquals("비밀번호를 다시 확인해주세요!",result.getMessage());
    }

    @Test
    void mismatchedPasswordErrorWhenLogin() {

        //given
        UserLoginRequestServiceDto userLoginRequestServiceDto = UserLoginRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .pw("erico")
                .build();

        String testId = testUtils.getTestUser().getId();
        User testEntity = testUtils.getTestUser();

        doReturn(Optional.of(testEntity)).when(userRepository).findUserById(testId);

        //when
        CustomException result = assertThrows(CustomException.class, ()-> userService.login(userLoginRequestServiceDto));

        //then
        assertEquals("비밀번호가 일치하지 않습니다. 비밀번호를 다시 확인해주세요!",result.getMessage());
    }

    @Test
    void mismatchedPasswordsErrorWhenDelete() {

        //given
        UserDeleteRequestServiceDto userDeleteRequestServiceDto = UserDeleteRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .pw(testUtils.getTestUser().getPw())
                .checkPw("erico")
                .build();

        String testId = testUtils.getTestUser().getId();
        User testEntity = testUtils.getTestUser();

        doReturn(Optional.of(testEntity)).when(userRepository).findUserById(testId);

        //when
        CustomException result = assertThrows(CustomException.class, ()-> userService.delete(userDeleteRequestServiceDto));

        //then
        assertEquals("비밀번호를 다시 확인해주세요!",result.getMessage());

    }

    @Test
    void mismatchedPasswordErrorWhenDelete() {

        //given
        UserDeleteRequestServiceDto userDeleteRequestServiceDto = UserDeleteRequestServiceDto
                .builder()
                .id(testUtils.getTestUser().getId())
                .pw(testUtils.getTestUser().getPw())
                .checkPw(testUtils.getTestUser().getPw())
                .build();

        String testId = testUtils.getTestUser().getId();
        User testEntity = testUtils.getTestUser();
        testEntity.setPw("erico");

        doReturn(Optional.of(testEntity)).when(userRepository).findUserById(testId);

        //when
        CustomException result = assertThrows(CustomException.class, ()-> userService.delete(userDeleteRequestServiceDto));

        //then
        assertEquals("비밀번호가 일치하지 않습니다. 비밀번호를 다시 확인해주세요!",result.getMessage());
    }
}