package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
public class LoginRegisterController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/usuarios")
    public String agregarUsuario(@RequestParam String nombre,
                                 @RequestParam String contrasena,
                                 @RequestParam String correoElectronico) throws IOException {
      
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setContraseña(passwordEncoder.encode(contrasena));
        usuario.setCorreoElectronico(correoElectronico);

        InputStream in = getClass().getResourceAsStream("/static/imagenes/fotoperfil.png");
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        int nRead;
        byte[] data = new byte[1024];
        while ((nRead = in.read(data, 0, data.length)) != -1) {
            buffer.write(data, 0, nRead);
        }
        buffer.flush();
        byte[] imagenPorDefecto = buffer.toByteArray();
        usuario.setFotoPerfil(imagenPorDefecto);

        usuarioRepository.save(usuario);
        return "redirect:/index";
    }
}
