package com.snowshare.SnowShare.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ComoFuncionaController {

    @GetMapping("/como-funciona")
    public String mostrarComoFunciona() {
        return "como-funciona";
    }
}
