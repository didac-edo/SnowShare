package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Categoria;
import com.snowshare.SnowShare.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

}
