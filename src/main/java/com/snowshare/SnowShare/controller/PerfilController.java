package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;
import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.ArticuloRepository;
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
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;

import java.io.IOException;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class PerfilController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ArticuloRepository articuloRepository;

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

        model.addAttribute("primerasImagenes", primerasImagenes);
        model.addAttribute("nombreUsuario", usuarioActual.getNombre());
        byte[] fotoPerfil = usuarioActual.getFotoPerfil();
        if (fotoPerfil != null) {
            String fotoPerfilBase64 = Base64.getEncoder().encodeToString(fotoPerfil);
            model.addAttribute("fotoPerfilBase64", fotoPerfilBase64);
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

    @PostMapping("/eliminarArticulo")
    public String eliminiarArticulo(@RequestParam("articuloId") Integer articuloId) {
        System.out.println("Eliminar artículo llamado con ID: " + articuloId);

        Articulo articulo = articuloRepository.findById(articuloId).orElse(null);

        if (articulo != null) {
            System.out.println("Encontrado articulo con ID: " + articulo.getIdArticulo());

            List<ImagenArticulo> imagenesArticulo = imagenArticuloRepository.findByArticuloIdArticulo(articulo.getIdArticulo());

            imagenArticuloRepository.deleteInBatch(imagenesArticulo);

            articuloRepository.deleteById(articulo.getIdArticulo());

            System.out.println("Articulo eliminado con ID: " + articulo.getIdArticulo());
        } else {
            System.out.println("No se encontró ImagenArticulo con ID: " + articuloId);
        }
        return "redirect:/perfil";
    }

}
