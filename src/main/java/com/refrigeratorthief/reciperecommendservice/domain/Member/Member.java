package com.refrigeratorthief.reciperecommendservice.domain.Member;

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
@Entity(name = "MEMBER")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
public class Member {

    @Id
    @Column(name = "MEMBER_ID", length = 45, nullable = false, unique = true)
    private String id;

    @Column(name = "MEMBER_NM", length = 20, nullable = false, unique = true)
    private String name;

    @Column(name = "MEMBER_PW", length = 45, nullable = false)
    private String pw;

    @Column(name = "MEMBER_CITY", length = 20, nullable = false)
    private String city;

    @Column(name = "MEMBER_DONG", length = 20, nullable = false)
    private String dong;
}
