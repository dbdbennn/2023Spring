package study.cookie_session;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.*;

import java.util.Enumeration;
import java.util.Map;

@RestController
public class SessionTestController {
    @PostMapping("/add_session_data")
    public String addSessionData(@RequestParam Map<String, String> params, HttpSession session) {
        for(String key : params.keySet()) {
            session.setAttribute(key, params.get(key));
        }
        // 세션 timeout 시간 설정 (단위는 초)
        // 쿠키와는 다르게 특정 시간에 반드시 만료되는 개념이 아니고, 매 요청시마다 만료 시간이 연장되는 개념으로 이해
        session.setMaxInactiveInterval(60);

        return "success";
    }

    @GetMapping("/show_all_session_data")
    public String showSessionData(HttpServletRequest request, HttpSession session) {
        Enumeration<String> attrs = session.getAttributeNames();
        // request 객체의 getSession 메소드 호출로 가져오는 것도 가능
        // Enumeration<String> attrs = request.getSession().getAttributeNames();
        String result = "";

        // 세션 ID는 쿠키를 통해 전송되어 해당 ID와 관련된 세션 정보를 찾는데 사용됨
        result += "getId : " + session.getId() + "\n";
        // 세션 생성 시점 (unix time)
        result += "getCreationTime : " + session.getCreationTime() + "\n";
        // 세션 마지막으로 접근한 시점
        result += "getLastAccessedTime : " + session.getLastAccessedTime() + "\n";
        // 세션 timeout 시간
        result += "getMaxInactiveInterval : " + session.getMaxInactiveInterval() + "\n";

        while(attrs.hasMoreElements()) {
            String attr = attrs.nextElement();
            result += attr + " : " + session.getAttribute(attr) + "\n";
        }

        return result.trim();
    }

    @DeleteMapping("/delete_all_session_data")
    public String deleteAllSessionData(HttpSession session) {
        // invalidate 메소드 호출하여 세션 데이터를 모두 삭제
        // (해당 세션은 파기되며 이후 세션을 요청하면 새로운 ID를 가진 새 새션이 생성됨)
        session.invalidate();

        return "success";
    }

    @GetMapping("/page")
    public String page() {
        return "page";
    }
}
