package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
public class LoginRegisterController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
        return "redirect:/index";
    }
}
