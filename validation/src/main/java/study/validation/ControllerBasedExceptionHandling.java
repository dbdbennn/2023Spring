package study.validation;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.xml.sax.SAXException;

import javax.xml.crypto.URIReferenceException;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.cert.CertificateEncodingException;
import java.util.*;

@RestController
public class ControllerBasedExceptionHandling {
    @GetMapping("/error_handle_from_method1")
    public void errorHandleFromMethod(@RequestParam("num") Integer num, HttpServletResponse response) throws Exception {
        try {
            response.setStatus(200);
            response.setHeader("Content-Type", "text/plain; charset=utf-8");
            // 0으로 나누면 에러 발생
            response.getWriter().write("계산 결과 : " + (10 / num));
        } catch(Exception e) {
            // 직접 catch 블록 내부에서 상태 코드값 설정하고 응답 메시지 반환
            response.setStatus(400);
            response.setHeader("Content-Type", "text/plain; charset=utf-8");
            response.getWriter().write("잘못된 파라미터 전달");
        }
    }

    @GetMapping(value="/error_handle_from_method2", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> honestErrorHandling() {
        try {
            boolean error = true;
            // 뭔가를 진행하다가 에러가 났다고 가정
            if(error) throw new Exception("심각한 에러 발생");
        } catch (Exception e) {
            // 직접 catch 블록 내부에서 상태 코드값 설정하고 응답 메시지 반환
            return ResponseEntity.badRequest().body("{ \"status\": 400, \"reason\": \"" + e.getMessage() + "\" }");
        }

        return ResponseEntity.ok().body("{ \"result\": \"success\" }");
    }

    // 컨트롤러의 내부에서 일어나는 ArithmeticException, ClassCastException은 모두 핸들러(arithmeticAndClassCastError)에서 처리
    @ExceptionHandler({ ArithmeticException.class, ClassCastException.class })
    // 반환해줄 HTTP 코드 (해당 예외와 밀접한 연관을 가진 에러 코드를 사용, 생략하면 200 OK 코드가 반환되므로 주의)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> arithmeticAndClassCastErrorHandler(Exception ex) {
        // 예외 발생 상태 코드와 메시지를 담을 맵 객체 생성
        Map<String, Object> error = new HashMap<>();
        error.put("status", 400);
        error.put("message", ex.getMessage());

        return error;
    }

    // 그 외의 모든 예외를 처리할 핸들러 정의 (예외 클래스가 Exception임을 주목)
    @ExceptionHandler(Exception.class)
    // 여기서는 500에러를 반환하도록 함
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, Object> allErrorHandler(Exception ex) {
        System.out.println(ex);
        Map<String, Object> error = new HashMap<>();
        error.put("status", 500);
        error.put("message", ex.getMessage());

        return error;
    }

    // (런타임 예외) ClassCastException 에러를 발생하는 핸들러
    // => arithmeticAndClassCastErrorHandler 에서 처리
    @GetMapping("/error_classcast")
    public String doClassCast() {
        Object o = "Hello";
        return ((Integer) o) + "";
    }

    // (런타임 예외) ArithmeticException 에러를 발생하는 핸들러
    // => arithmeticAndClassCastErrorHandler 에서 처리
    @GetMapping("/error_arithmetic")
    public String doArithmetic() {
        return (10 / 0) + "";
    }

    // 다양한 에러를 발생하는 핸들러
    // => allErrorHandler 에서 처리
    @GetMapping("/error_make")
    public String doMakeError() throws Exception {
        ArrayList<Exception> es = new ArrayList<>();

        es.add(new CertificateEncodingException("CertificateEncodingException 발생"));
        es.add(new URIReferenceException("URIReferenceException 발생"));
        es.add(new SAXException("SAXException 발생"));

        // 아무 예외나 발생시키기
        throw es.get(new Random().nextInt(es.size()));
    }

    // IOException 관련된 에러를 발생하는 핸들러
    // => allErrorHandler 에서 처리
    @GetMapping("/error_io")
    public String doIO() throws IOException {
        FileInputStream fis = new FileInputStream("no_such_file_exists.txt");
        fis.close();

        return "...";
    }

}
