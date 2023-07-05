package com.example.demo;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
// 이것은 기본 전략이므로, 생략 가능하지만 직접 써도 무방함
@Scope("singleton")
public class MySingletonBean {
    public MySingletonBean() {
        System.out.println("MySingletonBean constructor");
    }
}
