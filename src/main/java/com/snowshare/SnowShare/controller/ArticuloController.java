package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.*;
import com.snowshare.SnowShare.repository.*;
import com.snowshare.SnowShare.service.CustomUserDetailsService;
import com.snowshare.SnowShare.service.ImagenArticuloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

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

    @Autowired
    private ResenaRepository resenaRepository;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @GetMapping("/publicar-articulo")
    public String mostrarFormularioCrearArticulo(Model model) {
        Articulo articulo = new Articulo();
        List<Categoria> categorias = categoriaRepository.findAll();
        model.addAttribute("articulo", articulo);
        model.addAttribute("categorias", categorias);
        return "publicar-articulo";
    }

    @PostMapping("/publicar-articulo")
    public String guardarArticulo(@ModelAttribute("articulo") Articulo articulo,
                                  @RequestParam("imagenes") MultipartFile[] imagenes,
                                  Authentication authentication) {
        Usuario propietario = usuarioRepository.findByCorreoElectronico(authentication.getName());
        articulo.setPropietario(propietario);

        Articulo savedArticulo = articuloRepository.save(articulo);

        for (MultipartFile imagen : imagenes) {
            ImagenArticulo imagenArticulo = new ImagenArticulo();
            try {
                imagenArticulo.setImagen(imagen.getBytes());
            } catch (IOException e) {

                System.out.println("Error al leer la imagen: " + e.getMessage());
            }
            imagenArticulo.setArticulo(savedArticulo);
            imagenArticuloRepository.save(imagenArticulo);
        }

        return "redirect:/listado-articulos";
    }

    @GetMapping("/imagen_articulo/{idImagenArticulo}")
    public ResponseEntity<byte[]> getImagenArticulo(@PathVariable("idImagenArticulo") Integer idImagenArticulo) {
        ImagenArticulo imagenArticulo = imagenArticuloRepository.findById(idImagenArticulo).orElse(null);

        if (imagenArticulo != null) {
            byte[] imagen = imagenArticulo.getImagen();
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(imagen);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/listado-articulos")
    public String listarArticulos(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();

        System.out.println("El username es: " + usernameEmail);

        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);

        System.out.println("El Usuario acutal es: " + usuarioActual);

        List<Articulo> articulos = articuloRepository.findAll();
        List<Articulo> articulosNoPropios = articulos.stream()
                .filter(articulo -> !articulo.getPropietario().getIdUsuario().equals(usuarioActual != null ? usuarioActual.getIdUsuario() : null))
                .collect(Collectors.toList());

        Map<Integer, List<String>> imagenesPorArticulo = new HashMap<>();

        for (Articulo articulo : articulosNoPropios) {

            List<ImagenArticulo> imagenes = imagenArticuloService.getImagenesByArticulo(articulo);

            List<String> imagenesBase64 = new ArrayList<>();
            for (ImagenArticulo imagenArticulo : imagenes) {
                if (imagenArticulo != null) {
                    byte[] imagenBytes = imagenArticulo.getImagen();
                    String imagenBase64 = Base64.getEncoder().encodeToString(imagenBytes);
                    imagenesBase64.add(imagenBase64);
                } else {
                    System.out.println("Se encontró una imagen nula en la lista de imágenes del artículo");
                }
            }

            imagenesPorArticulo.put(articulo.getIdArticulo(), imagenesBase64);
        }

        model.addAttribute("usuarioActual", usuarioActual);
        model.addAttribute("articulos", articulos);
        model.addAttribute("imagenesPorArticulo", imagenesPorArticulo);

        return "listado-articulos";
    }

    @GetMapping("/listado-articulos/{idArticulo}")
    public String detalleArticulo(@PathVariable("idArticulo") Integer id, Model model) {
        Optional<Articulo> articuloOpt = articuloRepository.findById(id);

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);


        if (articuloOpt.isPresent()) {
            Articulo articulo = articuloOpt.get();
            model.addAttribute("articulo", articulo);

            List<ImagenArticulo> imagenesArticulo = imagenArticuloService.getImagenesByArticulo(articulo);
            List<String> imagenesBase64 = imagenesArticulo.stream()
                    .map(imagenArticulo -> Base64.getEncoder().encodeToString(imagenArticulo.getImagen()))
                    .collect(Collectors.toList());

            Map<Integer, List<String>> imagenesPorArticulo = new HashMap<>();
            imagenesPorArticulo.put(articulo.getIdArticulo(), imagenesBase64);

            model.addAttribute("imagenesPorArticulo", imagenesPorArticulo);

            List<Resena> resenas = resenaRepository.findByArticulo(articulo);
            model.addAttribute("resenas", resenas);

            Resena comentario = new Resena();
            model.addAttribute("comentario", comentario);
            model.addAttribute("usuario", usuarioActual);
        } else {
            return "error";
        }

        return "detalle_articulo";
    }

    @PostMapping("/listado-articulos/{idArticulo}/comentar")
    public String comentar(@PathVariable Integer idArticulo, @ModelAttribute Resena comentario, Principal principal, RedirectAttributes redirectAttributes) {

        Articulo articulo = articuloRepository.findById(idArticulo).orElseThrow(() -> new IllegalArgumentException("Artículo no encontrado"));

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);

        comentario.setArticulo(articulo);
        comentario.setUsuario(usuarioActual);

        resenaRepository.save(comentario);

        redirectAttributes.addFlashAttribute("success", "Comentario enviado con éxito");
        return "redirect:/listado-articulos/" + idArticulo;
    }

    @GetMapping("/articulos/{idArticulo}/editar")
    public String mostrarEditarArticulo(@PathVariable("idArticulo") Integer idArticulo, Model model) {

        Articulo articulo = articuloRepository.getById(idArticulo);

        model.addAttribute("articulo", articulo);

        return "editar_articulo";
    }

    @PostMapping("/articulos/{idArticulo}/editar")
    public String editarArticulo(@PathVariable("idArticulo") Integer idArticulo, @ModelAttribute("articulo") Articulo articulo, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "editar_articulo";
        }

        Articulo articuloExistente = articuloRepository.getById(idArticulo);
        articuloExistente.setTitulo(articulo.getTitulo());
        articuloExistente.setDescripcion(articulo.getDescripcion());
        articuloExistente.setCodigoPostal(articulo.getCodigoPostal());
        articuloExistente.setPrecioDia(articulo.getPrecioDia());
        articuloExistente.setDiasMinimo(articulo.getDiasMinimo());
        articuloExistente.setDescuentoPrecio(articulo.getDescuentoPrecio());

        articuloRepository.save(articuloExistente);

        return "redirect:/perfil";
    }

}
