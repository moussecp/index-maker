package com.bxy.indexmaker.web;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class HomeController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

}