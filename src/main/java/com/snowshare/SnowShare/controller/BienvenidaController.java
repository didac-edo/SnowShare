package com.snowshare.SnowShare.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BienvenidaController {

    @GetMapping("/bienvenida")
    public String mostrarBienvenida(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String nombreUsuario = auth.getName();
        model.addAttribute("nombreUsuario", nombreUsuario);
        return "bienvenida";
    }
}