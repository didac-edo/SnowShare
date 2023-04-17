package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping("/usuarios")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios";
    }

    @PostMapping("/usuarios")
    public String agregarUsuario(@RequestParam String nombre,
                                 @RequestParam String contrasena,
                                 @RequestParam String correoElectronico,
                                 @RequestParam("fotoPerfil") MultipartFile fotoPerfil) throws IOException {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContraseña(passwordEncoder.encode(contrasena));
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setFotoPerfil(fotoPerfil.getBytes());

        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }


}