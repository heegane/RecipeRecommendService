package com.refrigeratorthief.reciperecommendservice;

import com.refrigeratorthief.reciperecommendservice.domain.user.User;
import lombok.Getter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Component
public class TestUtils {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LocalDate testDate;
    private final LocalDate testDate2;
    private final User testUser;
    private final LocalDateTime testDateTime = LocalDateTime.parse("2023-05-20 14:55:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDateTime testDateTime2 = LocalDateTime.parse("2022-05-30 12:30:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    private final LocalDate testReturnDate;
    private final LocalDate testReturnDate2;

    public TestUtils(){

        testDate = LocalDate.of(2022,1,22);
        testDate2 = LocalDate.of(2022,8,28);
        testReturnDate = LocalDate.of(2024,3,2);
        testReturnDate2 = LocalDate.of(2024,9,1);
        testUser = User.builder()
                .id("asdf")
                .name("테스트")
                .pw("asdf")
                .city("사랑시 고백구")
                .dong("행복동")
                .build();
    }
}
