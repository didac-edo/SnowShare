package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.Categoria;
import com.snowshare.SnowShare.models.ImagenArticulo;
import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.ArticuloRepository;
import com.snowshare.SnowShare.repository.CategoriaRepository;
import com.snowshare.SnowShare.repository.ImagenArticuloRepository;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import com.snowshare.SnowShare.service.ImagenArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

@Controller
public class ArticuloController {

    @Autowired
    private ImagenArticuloService imagenArticuloService;

    @Autowired
    private ArticuloRepository articuloRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("/crear_articulo")
    public String mostrarFormularioCrearArticulo(Model model) {
        Articulo articulo = new Articulo();
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("articulo", articulo);
        model.addAttribute("categorias", categorias);
        return "crear_articulo";
    }

    @PostMapping("/crear_articulo")
    public String guardarArticulo(@ModelAttribute("articulo") Articulo articulo,
                                  @RequestParam("imagenes") MultipartFile[] imagenes,
                                  Authentication authentication) {
        // Obtener el usuario autenticado actual
        Usuario propietario = usuarioRepository.findByCorreoElectronico(authentication.getName());
        articulo.setPropietario(propietario);

        // Guardar el objeto "articulo" en la base de datos
        Articulo savedArticulo = articuloRepository.save(articulo);

        for (MultipartFile imagen : imagenes) {
            ImagenArticulo imagenArticulo = new ImagenArticulo();
            try {
                imagenArticulo.setImagen(imagen.getBytes());
            } catch (IOException e) {
                // Manejar la excepción de lectura de bytes aquí

                System.out.println("Error al leer la imagen: " + e.getMessage());
            }
            imagenArticulo.setArticulo(savedArticulo);
            imagenArticuloRepository.save(imagenArticulo);
        }

        //return "redirect:/todos_los_articulos";
        return "redirect:/articulos";
    }

    @GetMapping("/imagen_articulo/{idImagenArticulo}")
    public ResponseEntity<byte[]> getImagenArticulo(@PathVariable("idImagenArticulo") Integer idImagenArticulo) {
        ImagenArticulo imagenArticulo = imagenArticuloRepository.findById(idImagenArticulo).orElse(null);

        if (imagenArticulo != null) {
            byte[] imagen = imagenArticulo.getImagen();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG) // Asume que las imágenes son JPEG; cambia a MediaType.IMAGE_PNG para PNG
                    .body(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/articulos")
    public String listarArticulos(Model model) {
        List<Articulo> articulos = articuloRepository.findAll();
        Map<Integer, List<String>> imagenesPorArticulo = new HashMap<>();

        for (Articulo articulo : articulos) {
            List<ImagenArticulo> imagenes = imagenArticuloService.getImagenesByArticulo(articulo);

            List<String> imagenesBase64 = new ArrayList<>();
            for (ImagenArticulo imagenArticulo : imagenes) {
                if (imagenArticulo != null) {
                    byte[] imagenBytes = imagenArticulo.getImagen();
                    String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
                    imagenesBase64.add(imagenBase64);
                } else {
                    // Puedes imprimir un mensaje de advertencia o manejar el caso de objeto nulo de otra manera
                    System.out.println("Se encontró una imagen nula en la lista de imágenes del artículo");
                }
            }

            imagenesPorArticulo.put(articulo.getIdArticulo(), imagenesBase64);
        }

        System.out.println("Mapa de imágenes por artículo: " + imagenesPorArticulo);

        model.addAttribute("articulos", articulos);
        model.addAttribute("imagenesPorArticulo", imagenesPorArticulo);

        return "articulos";
    }
}
