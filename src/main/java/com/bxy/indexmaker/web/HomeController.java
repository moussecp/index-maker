package com.bxy.indexmaker.web;


import com.bxy.indexmaker.service.RowContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private RowContentService rowContentService;

    @RequestMapping("/")
    String home(Model model) {
        model.addAttribute("firstCells", rowContentService.getFirstCells());
        return "home";
    }
}