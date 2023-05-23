package com.refrigeratorthief.reciperecommendservice.domain.user;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity(name = "USER")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class User {

    @Id
    @Column(name = "USER_ID", length = 45, nullable = false, unique = true)
    private String id;

    @Column(name = "USER_NM", length = 20, nullable = false, unique = true)
    private String name;

    @Column(name = "USER_PW", length = 45, nullable = false)
    private String pw;

    @Column(name = "USER_CITY", length = 20, nullable = false)
    private String city;

    @Column(name = "USER_DONG", length = 20, nullable = false)
    private String dong;
}
