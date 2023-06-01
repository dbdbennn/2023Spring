package study.cookie_session;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
