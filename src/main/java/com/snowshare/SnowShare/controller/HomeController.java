package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/usuarios")
    public String mostrarFormularioCrearUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuarios";
    }

    @GetMapping("/iniciarSesion")
    public String mostrarPaginaIniciarSesion(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "iniciarSesion";
    }

    @PostMapping("/usuarios")
    public String agregarUsuario(@RequestParam String nombre,
                                 @RequestParam String contrasena,
                                 @RequestParam String correoElectronico,
                                 @RequestParam String fotoPerfil) {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContraseña(contrasena);
        usuario.setCorreoElectronico(correoElectronico);
        usuario.setFotoPerfil(fotoPerfil);

        usuarioRepository.save(usuario);
        return "redirect:/usuarios";
    }

    @PostMapping("/iniciarSesion")
    public String iniciarSesion(@RequestParam String correoElectronico,
                                @RequestParam String contrasena,
                                Model model) {
        Optional<Usuario> usuarioOptional = usuarioRepository.findByCorreoElectronico(correoElectronico);

        if (usuarioOptional.isPresent()) {
            Usuario usuario = usuarioOptional.get();

            if (usuario.getContraseña().equals(contrasena)) {
                model.addAttribute("usuario", usuario);
                System.out.println("FUNCIONA");
                return "paginaInicio"; // Aquí puedes redirigir a la página de inicio después del inicio de sesión exitoso.
            }
        }

        model.addAttribute("error", "Correo electrónico o contraseña incorrectos.");
        return "iniciarSesion";
    }

}