package com.example.study_jpa.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@RequiredArgsConstructor
@Setter
@Getter
@ToString
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, nullable=false)
    @NonNull
    private String username;

    @Column(nullable=false)
    @NonNull
    private String password;

    @Column(nullable=false)
    private String name;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    // JSON 문자열 형식의 데이터를 보낼때 ISO-8601 형식의 날짜 문자열을 Date 타입으로 변경할 수 있도록 하기 위해서 설정
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    private Date createdAt;
}