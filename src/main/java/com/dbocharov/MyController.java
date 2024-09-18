package com.dbocharov;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MyController {
    @GetMapping(value = {"/", "greeting"})
    public String greeting() {
        return "Hello world!";
    }
}