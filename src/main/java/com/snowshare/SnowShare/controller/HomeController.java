package com.snowshare.SnowShare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    //kike

    @GetMapping("/")
    public String index() {
        return "index";
    }
}