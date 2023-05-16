package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.*;
import com.snowshare.SnowShare.repository.*;
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

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
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

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ResenaRepository resenaRepository;

    @GetMapping("/perfil")
    public String mostrarPerfil(Model model) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);

        List<Reserva> reservas = reservaRepository.findByUsuarioIdUsuario(usuarioActual.getIdUsuario());
        List<String> descReservas = new ArrayList<>();

        for (Reserva reserva : reservas) {
            long diasReservados = ChronoUnit.DAYS.between(reserva.getFechaInicio(), reserva.getFechaFin());
            BigDecimal diasReservadosBD = new BigDecimal(diasReservados);
            BigDecimal precio = reserva.getArticulo().getPrecioDia();
            BigDecimal precioTotal = precio.multiply(diasReservadosBD);

            String descReserva;
            if (reserva.getArticulo().getDescuentoPrecio() == null || reserva.getArticulo().getDescuentoPrecio() == 0) {
                descReserva = "La reserva ha sido de " + diasReservadosBD + " dias y el precio total de la reserva ha sido de " + precioTotal.setScale(2, RoundingMode.HALF_UP) + "€";
            } else {
                BigDecimal descuentoDecimal = new BigDecimal(reserva.getArticulo().getDescuentoPrecio()).divide(new BigDecimal(100));
                BigDecimal cantidadDescuento = precioTotal.multiply(descuentoDecimal);
                BigDecimal precioConDescuento = precioTotal.subtract(cantidadDescuento);
                descReserva = "La reserva ha sido de " + diasReservadosBD + " dias y el precio total de la reserva con un descuento del " + reserva.getArticulo().getDescuentoPrecio() + "% ha sido de " + precioConDescuento.setScale(2, RoundingMode.HALF_UP) + "€";
            }
            descReservas.add(descReserva);
        }

        model.addAttribute("descReservas", descReservas);
        model.addAttribute("reservas", reservas);


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
        System.out.println("El cambio de foto es: " + fotoPerfil.toString());
        usuarioService.cambiarFotoPerfil(fotoPerfil.getBytes());
        System.out.println("Se ha cambiado la foto correctamente");
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.IMAGE_JPEG).body(fotoPerfil.getBytes());
    }

    @PostMapping("/eliminarArticulo")
    public String eliminiarArticulo(@RequestParam("articuloId") Integer articuloId) {
        System.out.println("Eliminar artículo llamado con ID: " + articuloId);

        Articulo articulo = articuloRepository.findById(articuloId).orElse(null);

        if (articulo != null) {
            System.out.println("Encontrado articulo con ID: " + articulo.getIdArticulo());

            List<Reserva> reservas = reservaRepository.findByArticuloIdArticulo(articulo.getIdArticulo());
            reservaRepository.deleteInBatch(reservas);

            List<Resena> resenas = resenaRepository.findByArticuloIdArticulo(articulo.getIdArticulo());
            resenaRepository.deleteInBatch(resenas);

            List<ImagenArticulo> imagenesArticulo = imagenArticuloRepository.findByArticuloIdArticulo(articulo.getIdArticulo());
            imagenArticuloRepository.deleteInBatch(imagenesArticulo);

            articuloRepository.deleteById(articulo.getIdArticulo());

            System.out.println("Articulo eliminado con ID: " + articulo.getIdArticulo());
        } else {
            System.out.println("No se encontró ImagenArticulo con ID: " + articuloId);
        }
        return "redirect:/perfil";
    }

    @PostMapping("/eliminarReserva")
    public String eliminiarReserva(@RequestParam("reservaId") Integer reservaId) {
        System.out.println("Eliminar reserva llamado con ID: " + reservaId);

        Reserva reserva = reservaRepository.findById(reservaId).orElse(null);

        if (reserva != null) {
            System.out.println("Encontrado reserva con ID: " + reserva.getIdReserva());

            reservaRepository.deleteById(reserva.getIdReserva());

            System.out.println("Reserva eliminado con ID: " + reserva.getIdReserva());
        } else {
            System.out.println("No se encontró articulo con ID: " + reservaId);
        }
        return "redirect:/perfil";
    }

}
