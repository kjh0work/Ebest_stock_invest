package com.example.ebest_open_api.app_test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AppController {

    @GetMapping("/welcome")
    public String welcome(){
        return "index";
    }

}
