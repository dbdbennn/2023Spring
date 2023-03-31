package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		// run 메서드의 반환값이 Bean들이 저장될 컨테이너로 활용되는 ApplicationContext 타입의 객체
		ApplicationContext context = SpringApplication.run(DemoApplication.class, args);
		// 모든 Bean들의 이름 가져와서
		String[] beanNames = context.getBeanDefinitionNames();
		// 확인해보기
		for (String beanName : beanNames) {
			// 단, 기본적으로 스프링에서 제공하는 Bean들은 가급적 필터링해서 보여주기 (전부 다 걸러지진 않음)
			if(!beanName.startsWith("org.") && !beanName.startsWith("spring.")) {
				System.out.println(beanName);
			}
		}

		MyBean bean1 = (MyBean) context.getBean("myBean"); //object로 변환이 되어있어서 형변환 시켜죠야함
		MyBean bean2 = (MyBean) context.getBean("myBean"); //object로 변환이 되어있어서 형변환 시켜죠야함
		System.out.println(bean1 == bean2); // 하나의 객체임. true

		GreetingService greetingService = (GreetingService) context.getBean("greetingService");
		greetingService.greet();
//		System.out.println(myBean);
//		Person person = (Person) context.getBean("person");
//		System.out.println(person);
//		MyCalculatorService calculatorService = (MyCalculatorService) context.getBean("myCalculatorService");
//		calculatorService.calcAdd(3, 5);
	}


}
