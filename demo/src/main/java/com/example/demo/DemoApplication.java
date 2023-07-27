package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

// 생성된 Bean 보관하는 창고 역할
@SpringBootApplication
@ComponentScan("com.example.demo")
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
		System.out.println(bean1);
		Person person = (Person) context.getBean("person");
		System.out.println(person);
		MyCalculatorService calculatorService = (MyCalculatorService) context.getBean("myCalculatorService");
		System.out.println(calculatorService.calcAdd(3, 5));


		// 이 코드를 실행하기 전 부트 어플리케이션이 시작되는 시점에 이미 (싱글턴) 객체 생성은 모두완료되었으며, 아무리 여러번 요청해도 똑같은 객체가 반환됨
		MySingletonBean mySingletonBean1 = (MySingletonBean)
				context.getBean("mySingletonBean");
		MySingletonBean mySingletonBean2 = (MySingletonBean)
				context.getBean("mySingletonBean");
		// 완전히 같은 객체이므로, true
		System.out.println("mySingletonBean1 == mySingletonBean2 : " + (mySingletonBean1
				== mySingletonBean2));

		// 직접적으로 Bean 객체를 요청하는 경우에 생성자를 통해 매번 Bean 생성을 하므로 이 코드를실행하는 시점에 2개의 Bean 객체가 만들어 짐
		MyPrototypeBean myPrototypeBean1 = (MyPrototypeBean)
				context.getBean(MyPrototypeBean.class);
		MyPrototypeBean myPrototypeBean2 = (MyPrototypeBean)
				context.getBean(MyPrototypeBean.class);
		// 서로 다른 객체이므로, false
		System.out.println("myPrototypeBean1 == myPrototypeBean2 : " + (myPrototypeBean1
				== myPrototypeBean2));
	}


}
