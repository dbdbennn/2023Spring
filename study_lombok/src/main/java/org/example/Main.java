package org.example;
import lombok.*;

import java.io.Serializable;

/*
@Data
class MyClass implements Serializable { // 조건 3
    // 조건 1
    private String value1;
    private String value2;

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    // 조건 2
    public MyClass() {
    }
}
*/

//@Getter @Setter
//@NoArgsConstructor
//@AllArgsConstructor
//// @RequiredArgsConstructor
//@ToString
//class MyClass implements Serializable{
//    private String value1;
//    private Integer value2;
//    @ToString.Exclude private String value3;
//
//
//
//}

@Builder
@Data
@NoArgsConstructor @RequiredArgsConstructor @AllArgsConstructor
class MyClass implements Serializable {
    @NonNull private String value1;
    private Integer value2;
    @ToString.Exclude private String value3;
}


public class Main {
    public static void main(String[] args) {
        // MyClass mc = new MyClass("value1", 1000, "value3");
        // System.out.println(mc.toString());
        MyClass mc = MyClass.builder()
                .value1("hello")
                .value3("world")
                .build();
    }
}