package com.example.springbootonminikube;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @GetMapping("/data")
    String getMessage() {
        return "This message from Pod";
    }
}
