package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;

@Controller
public class BienvenidaController {

    @Autowired
    UsuarioRepository usuarioRepository;

    @GetMapping("/bienvenida")
    public String mostrarBienvenida(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String correoElectronico = auth.getName();

        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        String nombreUsuario = usuario.getNombre();
        byte[] imagenBytes = usuario.getFotoPerfil();
        String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
        model.addAttribute("nombreUsuario", nombreUsuario);
        model.addAttribute("rutaImagenPerfil", imagenBase64);
        return "bienvenida";
    }
}