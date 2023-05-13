package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/iniciar-registrar-sesion")
    public String iniciarRegistrarSesion(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "iniciar-registrar-sesion";
    }
}
