package com.example.study_jpa;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

@Entity
public class MyEntity {
    enum MyEnum { HELLO, WORLD }
    enum MyAnotherEnum { VALUE1, VALUE2 }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique=true, nullable=false)
    @NotNull
    private String uniqueStringColumn;

    @Column(name="my_int_column")
    private Integer intColumn;

    @Column
    private Double doubleColumn;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateColumn;

    @Column @Enumerated(EnumType.STRING)
    private MyEnum enum1;

    // ORDINAL이 기본값이지만 사용이 권장되지 않음 (열거형 클래스 내부의 상수 순서가 변경될 경우 문제가 발생할 수 있음)
    @Column @Enumerated(EnumType.ORDINAL)
    private MyAnotherEnum enum2;

    @Lob
    private String lobString;

    // longblob (BLOB)
    @Lob
    private byte[] lobBytes;

    @Transient
    private String transientValue;
}
