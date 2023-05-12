package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {

    @Autowired
    private CategoriaService categoriaService;


    @GetMapping("/index")
    public String mostrarIndex(Model model) {
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "index";
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categorias", categoriaService.getAllCategorias());
        return "index";
    }
}
