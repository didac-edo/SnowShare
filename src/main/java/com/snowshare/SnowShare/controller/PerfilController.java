package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;
import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.ImagenArticuloRepository;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import com.snowshare.SnowShare.service.UsuarioService;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);

        List<ImagenArticulo> primerasImagenes = imagenArticuloRepository.findFirstImagesByUser(usuarioActual.getIdUsuario());

        List<String> imagenesBase64 = primerasImagenes.stream()
                .map(imagenArticulo -> Base64.getEncoder().encodeToString(imagenArticulo.getImagen()))
                .collect(Collectors.toList());
        model.addAttribute("imagenesBase64", imagenesBase64);

        System.out.println("El nombreUsuario actual es: " + usuarioActual.getNombre());

        for (ImagenArticulo imagenArticulo : primerasImagenes) {
            System.out.println("Imagen base64: " + Base64.getEncoder().encodeToString(imagenArticulo.getImagen()));
        }

        model.addAttribute("primerasImagenes", primerasImagenes);
        model.addAttribute("nombreUsuario", usuarioActual.getNombre());
        byte[] fotoPerfil = usuarioActual.getFotoPerfil();
        if (fotoPerfil != null) {
            String fotoPerfilBase64 = Base64.getEncoder().encodeToString(fotoPerfil);
            model.addAttribute("fotoPerfilBase64", fotoPerfilBase64);
            System.out.println("FotoPerfil: " + fotoPerfilBase64);

        }
        return "perfil";
    }

    @PostMapping("/actualizarNombre")
    public ResponseEntity<Void> actualizarNombre(@RequestBody Map<String, String> body) {
        String nombre = body.get("nombre");
        System.out.println("El nombre ha actualizar es: " + nombre);
        usuarioService.actualizarNombre(nombre);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/cambiarFotoPerfil")
    public ResponseEntity<?> cambiarFotoPerfil(@RequestPart("fotoPerfil") MultipartFile fotoPerfil) throws IOException {
        usuarioService.cambiarFotoPerfil(fotoPerfil.getBytes());
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(fotoPerfil.getBytes());
    }



}
