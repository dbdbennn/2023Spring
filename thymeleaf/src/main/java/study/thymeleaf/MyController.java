package study.thymeleaf;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MyController {
    @GetMapping("/template1")
    public String template1Page() {
        // 여기서 반환하는 문자열 "template1"은 응답 메시지의 바디에 포함될 문자열이 아니고,
        // 렌더링할 파일의 이름("template1.html")임을 유의

        // 템플릿 엔진을 사용함을 의미
        // templates 폴더가 가장 상위 폴더임
        // 확장자는 쓰지 않아도 됨
        return "template1";
    }

    @GetMapping("/template2")
    // Model은 더 이상 전달받을 필요가 없고 반환 타입을 ModelAndView 타입으로 변경
    public ModelAndView template2Page() {
        // 템플릿 파일의 이름을 생성자로 전달
        ModelAndView mav = new ModelAndView("template2");
        // addObject를 이용해서 템플릿 파일에서 출력할 정보 추가
        mav.addObject("value1", 100);
        mav.addObject("value2", "Hello, Paragraph!");

        Book book = new Book("김철수", "부자되는 방법", 10000);
        mav.addObject("mybook", book);

        // ModelAndView 객체 반환
        return mav;
    }

    @GetMapping("/template3")
    public String template3(Model m) {
        m.addAttribute("condition1", true);
        m.addAttribute("condition2", false);

        Book book = new Book("김철수", "부자되는 방법", 10000);
        book.setSale(true);
        book.setSaleAmount(0.3);
        m.addAttribute("book", book);

        List<Book> bookList = new ArrayList<>();
        bookList.add(new Book("김철수", "부자되는 방법 1", 10000));
        bookList.add(new Book("김철수", "부자되는 방법 2", 20000, true, 0.2));
        bookList.add(new Book("김철수", "부자되는 방법 3", 30000));
        bookList.add(new Book("김철수", "부자되는 방법 4", 40000, true, 0.3));
        m.addAttribute("book_list", bookList);

        Map<String, Book> bookMap = new HashMap<>();
        bookMap.put("book1", new Book("김철수", "부자되는 방법 1", 10000));
        bookMap.put("book2", new Book("김철수", "부자되는 방법 2", 20000, true, 0.2));
        bookMap.put("book3", new Book("김철수", "부자되는 방법 3", 30000));
        bookMap.put("book4", new Book("김철수", "부자되는 방법 4", 40000, true, 0.3));
        m.addAttribute("book_map", bookMap);

        m.addAttribute("grade", "A");

        return "template3";
    }
}