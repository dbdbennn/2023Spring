package com.example.study_jpa;

import com.example.study_jpa.entity.User;
import com.example.study_jpa.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    // C
    @PostMapping
    public ResponseEntity<User> postUser(@RequestBody User user) {
        user.setCreatedAt(new Date());
        userRepository.save(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/users/" + user.getId());
        return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
    }

    // R
    @GetMapping("")
    public Iterable<User> getAllUser() {
        return userRepository.findAll();
    }

    // R
    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable("id") Integer id) {
        Optional<User> user = userRepository.findById(id);
        // https://stackoverflow.com/questions/26550124/spring-returning-empty-http-responses-with-responseentityvoid-doesnt-work
        // If returned value is Optional, there are convenient method, returned ok() or notFound():
        return ResponseEntity.of(user);
    }

    // U
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable("id") Integer id, @RequestBody User modified) {
        if(modified.getId() == null || modified.getId() != id) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsById(modified.getId())) {
            System.out.println(modified);
            userRepository.save(modified);
            return new ResponseEntity<>(modified, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // D
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable("id") Integer id, HttpServletResponse response) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
            response.setStatus(HttpStatus.NO_CONTENT.value());
        } else {
            response.setStatus(HttpStatus.NOT_FOUND.value());
        }
    }
}