package com.example.demo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

// 기본 설정 파일을 사용한다고 가정
@Service
// EnvironmentAware 인터페이스 구현 필요
// 그냥 Autowired를 쓰지 못하는 이유는 Environment 객체 생성 시점이 더 늦은 시점에 진행되기 때문
public class GreetingService implements EnvironmentAware {
    private String message;
    // 해당 메서드를 오버라이드하면 Environment 객체에 대한 의존성 주입이 진행됨
    @Override
    public void setEnvironment(Environment environment) {
    // getProperty 메서드 호출해서 직접 속성값의 속성 이름 입력
        message = environment.getProperty("greeting.message");
    }
    public void greet() {
        System.out.println(message);
    }
}
