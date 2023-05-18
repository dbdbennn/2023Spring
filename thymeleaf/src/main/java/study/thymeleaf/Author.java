package study.thymeleaf;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

// Q) Lombok을 활용하여 생성자, 게터, 세터 대체
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Author implements Serializable {
    private String name;
}