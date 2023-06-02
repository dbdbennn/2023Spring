package study.cookie_session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class CookieTestController {
    @PostMapping("/add_cookie_data")
    public String addCookieData(@RequestParam Map<String, String> params, HttpServletResponse response) {
        for(String key : params.keySet()) {
            // 문자열 타입의 키, 값을 이용하여 쿠키 정보 생성 (스펙상 문자열 정보만 저장 가능)
            Cookie c = new Cookie(key, params.get(key).toString());
            // 쿠키의 만료 시간 지정, 단위는 초 (여기서는 60초로 설정)
            c.setMaxAge(60);
            // 자바스크립트를 통한 쿠키 접근 불허
            c.setHttpOnly(true);
            // HTTPS 통신으로만 쿠키 데이터 전송
            // c.setSecure(true);
            // 특정 경로에 접근할 때에만 해당 쿠키 데이터 전송
            // c.setPath("/page");
            response.addCookie(c);
        }

        return "success";
    }

    @GetMapping("/get_cookie_data")
    public String getCookieData(
            @CookieValue(value="data1", defaultValue="Hello") String data1,
            @CookieValue(value="asdf", required=false) String asdf) {
        return data1 + " " + asdf;
    }


    @GetMapping("/show_all_cookie_data")
    public String showCookieData(HttpServletRequest request) {
        String result = "no cookies";
        // 요청 메시지를 통해 받은 모든 쿠키 객체 가져오기
        Cookie[] cookies = request.getCookies();
        // 저장된 쿠키가 하나도 없는 경우에는 빈 배열이 아니라 null을 반환함을 유의
        if(cookies != null) {
            result = "";
            for(Cookie c : cookies) {
                // 쿠키의 이름(=키)와 값을 모두 출력
                result += c.getName() + " : " + c.getValue() + "\n";
            }
        }

        return result.trim();
    }
    @DeleteMapping("/delete_all_cookie_data")
    public String deleteAllCookieData(HttpServletRequest request, HttpServletResponse response) {
        // 모든 쿠키 삭제
        Cookie[] cookies = request.getCookies();
        if(cookies != null) {
            for(Cookie c : cookies) {
                Cookie deleted = new Cookie(c.getName(), "");
                deleted.setMaxAge(0); // 만료 기간을 0초로 설정
                response.addCookie(deleted);
            }
        }

        return "success";
    }

}
