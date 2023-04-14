package study.mvc;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/test")
public class TestController {
    Integer counter = 0;
    // "Hello, World!" 출력
    @GetMapping(value = "/hello", produces = MediaType.TEXT_PLAIN_VALUE)
    public String hello()  {
        return "Hello, World!";
    }

    // reverse Strings
    // GET /reverse?words=hello,world,mirim
    // 일반 텍스트로 "mirim,world,hello"
    @GetMapping(value = "/reverse", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String reverse(@RequestParam(value = "word", required = true) String word)  {
        String result = "";
        String[] words = word.split(",");
        // 거꾸로 돌림
        for (int i = words.length - 1; i >= 0; i--) {
            result += words[i] +",";
        }

        // 마지막 문자열 제거
        result = result.substring(0, result.length() - 1);

        return result;
    }

    // Multiply Two Numbers
    // /mul?num1=3&num2=4 => 12
    // /add?num1=3&num2=4 => 7
    // /sub?num1=3&num2=4 => -1
    // @PathVariable, @RequestParam 사용
    @GetMapping(value = "/calc/{op}", produces = MediaType.TEXT_PLAIN_VALUE)
    public String calc(
            @PathVariable("op") String op,
            @RequestParam("num1") Integer num1,
            @RequestParam("num2") Integer num2
    ) throws Exception {
        switch (op){
            case "mul" : return "" + (num1 * num2);
            case "add" : return "" + (num1 + num2);
            case "sub" : return "" + (num1 - num2);
            default: throw new Exception("잘못된 연산자");
        }

    }

    // 매번 호출할 때마다 해당 핸들러 메서드가 호출된 횟수를 반환
    @PostMapping(value = "/count", produces = MediaType.TEXT_PLAIN_VALUE)
    public String count() {
        return "" + (++counter);
    }

    @GetMapping(value = "/result_test", produces = MediaType.APPLICATION_JSON_VALUE)
    public SuccessResult resultTest() {
        return new SuccessResult();
    }

    @GetMapping(value = "/result_test2", produces = MediaType.APPLICATION_JSON_VALUE)
    public Result resultTest2(){
        Result result = new Result();
        result.result = "success";
        result.code = 1000;

        return result;
    }

    @GetMapping(value = "/result_test3", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Object> resultTest3() {
        Map<String, Object> result = new HashMap<>();
        result.put("result", "success");
        result.put("code", 1000);

        return result;
    }

}

class SuccessResult{
    public String result = "success";
}

class Result{
    public String result;
    public Integer code;
}