package com.example.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// 요청하는 시점마다 Bean을 생성하도록 하기 위해서 다음과 같이 prototype으로 옵션 설정
@Scope("prototype")
public class MyPrototypeBean {
    public MyPrototypeBean() {
        System.out.println("MyPrototypeBean constructor");
    }
}
