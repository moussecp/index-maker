package web;


import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.*;
import org.springframework.web.bind.annotation.*;

@RestController
@EnableAutoConfiguration
public class HomeController {

    @RequestMapping("/")
    String home() {
        return "Hello World!";
    }

}