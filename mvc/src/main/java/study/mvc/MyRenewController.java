package study.mvc;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

// @Controller
@RestController
@RequestMapping("/renew")
public class MyRenewController {
    // produces 옵션을 통해서 미디어 타입 지정 가능 (유추해서 자동으로 지정하게 할 수도 있지만 가급적 써주는 것을 권장)
    @GetMapping(value = "/echo", produces = MediaType.TEXT_PLAIN_VALUE)
    // 반환한 문자열이 바로 응답 메시지의 바디 데이터에 삽입될 수 있도록 @ResponseBody 어노테이션 추가 (@RestController를 사용하면 생략 가능)
    // @ResponseBody
    public String echo(@RequestBody byte[] content) {
        // 메서드 정보 접근할 필요 없음 (GET 메서드)
        // 주소 정보 접근할 필요 없음 (@PathVariable 사용)
        // 쿼리 스트링 정보 접근할 필요 없음 (@RequestParam 사용)
        // 프로토콜, HTTP 버전 정보 접근할 필요가 보통 없음
        // 헤더 정보 접근할 필요 없음 (@RequestHeader 사용)
        // 요청 메시지의 바디 데이터 접근은 @RequestBody 어노테이션을 이용해서 전달받을 수 있음
        String bytesToString = new String(content, StandardCharsets.UTF_8);
        System.out.println(bytesToString);

        // Content-Type 헤더의 경우 produces 옵션을 제공하여 미디어 타입 지정 가능
        return bytesToString;
    }

    @GetMapping(value = "/hello-html", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String helloHTML() {
        return "<h1>Hello</h1>";
    }

    @GetMapping(value = "/hello-xml", produces = MediaType.TEXT_XML_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public String helloXML() {
        return "<text>Hello</text>";
    }

    @GetMapping(value = "/hello-json")
    @ResponseStatus(HttpStatus.OK)
    public String helloJSON() {
        return "{ \"data\": \"hello\" }";
    }

    @GetMapping(value = "/echo-repeat", produces = MediaType.TEXT_PLAIN_VALUE)
    // @RequestHeader 어노테이션을 통해서 X-Repeat-Count에 적힌 숫자 정보 가져오고 없으면 1로 초기화
    public String echoRepeat(@RequestParam("word") String word, @RequestHeader(value = "X-Repeat-Count", defaultValue = "1") Integer repeatCount) throws IOException {
        String result = "";
        for(int i=0;i<repeatCount;i++) {
            result += word;
        }
        return result;
    }

    @GetMapping(value = "/dog-image", produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] dogImage() throws IOException {
        // resources 폴더의 static 폴더에 이미지 있어야 함
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        // 파일의 바이트 데이터 모두 읽어오기
        byte[] bytes = Files.readAllBytes(file.toPath());

        return bytes;
    }

    @GetMapping(value = "/dog-image-file", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
// 헤더를 직접 조정하고 싶은 경우 ResponseEntity 타입을 반환하도록 설정 가능
// (꺽쇠 안에는 응답 메시지의 바디 데이터에 포함될 타입을 지정)
    public ResponseEntity<byte[]> dogImageFile() throws IOException {
        File file = ResourceUtils.getFile("classpath:static/dog.jpg");
        byte[] bytes = Files.readAllBytes(file.toPath());

        String filename = "dog.jpg";
        // 헤더 값 설정
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=" + filename);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    private ArrayList<String> wordList = new ArrayList<>();

    // Q1) 위의 ArrayList에 단어를 추가하는 메서드, 스프링답게 핸들러 메서드 만들어보기
    @PostMapping(value = "/words", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public void addWords(@RequestBody String bodyString) {
        String[] words = bodyString.split("\n");
        for (String w : words) wordList.add(w.trim());
    }

    // Q2) 저장된 모든 단어 보여주기 메서드, 스프링답게 핸들러 메서드 만들어보기
    @GetMapping(value = "/words", produces = MediaType.TEXT_PLAIN_VALUE)
    public String showWords(){
        String allWords = String.join(", ", wordList);
        return allWords;
    }

    @PostMapping("/test")
    // http://localhost:1234/renew/test?value1=123&value2=456
    // @ModelAttribute를 타입 앞에 붙여주고 메서드의 파라미터 값으로 전달되게 함
    public String cotmmandObjectTest(@ModelAttribute MyCommandObject myCommandObject) {
        return myCommandObject.toString();
    }

    // 요청 메시지의 Content-Type이 "applicaion/json"인 요청을 받아들이기 위해서 consumes를 MediaType.APPLICATION_JSON_VALUE로 설정
    // 응답 메시지의 Content-Type이 "applicaion/json"이므로 produces를 MediaType.APPLICATION_JSON_VALUE로 설정
    @PostMapping(value = "/json-test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // 반환 타입으로 MyJsonData를 설정하였고 ResponseBody 어노테이션을 통해 해당 값이 메시지 컨버터를 통해서 직렬화되어야 함을 알림
    @ResponseBody

    /* body
    {
        "value1" : "hello",
        "value2" : 123
    }
    */
    // RequestBody 어노테이션을 통해 요청 메시지의 바디에 포함된 JSON 문자열이 메시지 컨버터를 통해서 역직렬화되어 객체로 변환되어야 함을 알림
    public MyJsonData jsonTes(@RequestBody MyJsonData myJsonData) {
        System.out.println(myJsonData);
        return myJsonData;
    }

    @PostMapping(value = "/student-test",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    // 반환 타입으로 MyJsonData를 설정하였고 ResponseBody 어노테이션을 통해 해당 값이 메시지 컨버터를 통해서 직렬화되어야 함을 알림
    @ResponseBody
    public MyStudentData studentTest(@RequestBody MyStudentData myStudentData){
        System.out.println(myStudentData);
        return myStudentData;
    }

    // http://localhost:1234/renew/github/dbdbennn
    @GetMapping(value = "/github/{user}", produces = MediaType.APPLICATION_JSON_VALUE)
    public String githubuser(@PathVariable("user") String user) {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 메시지 생성 및 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        RequestEntity<String> requestEntity = new RequestEntity<>(
                null, null, HttpMethod.GET, URI.create("https://api.github.com/users/" + user));

        ResponseEntity<String> response = restTemplate.exchange(requestEntity, String.class);

        return response.getBody();
    }

    @GetMapping(value = "/kanye", produces = MediaType.APPLICATION_JSON_VALUE)
    public Kanye kanye() {
        RestTemplate restTemplate = new RestTemplate();

        // 요청 메시지 생성 및 설정
        RequestEntity<String> requestEntity = new RequestEntity<>(
                null, null, HttpMethod.GET, URI.create("https://api.kanye.rest/"));

        ResponseEntity<Kanye> response = restTemplate.exchange(requestEntity, Kanye.class);

        return response.getBody();
    }
}











