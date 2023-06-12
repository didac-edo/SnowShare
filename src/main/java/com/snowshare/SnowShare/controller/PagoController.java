package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.models.*;
import com.snowshare.SnowShare.repository.ReservaRepository;
import com.snowshare.SnowShare.repository.TarjetaCreditoRepository;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class PagoController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private TarjetaCreditoRepository tarjetaCreditoRepository;

    @GetMapping("/pagarReserva")
    public String mostrarPagoReserva(/*@RequestParam("reservaId") Long reservaId, */Model model) {

        System.out.println("Ha entrado en el mostrarPagoReserva GET");

        /*Reserva reserva = reservaRepository.findById(Math.toIntExact(reservaId)).orElse(null);
        model.addAttribute("reserva", reserva);
*/
        /*UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);*/

        //TarjetaCredito tarjetaCredito = tarjetaCreditoRepository.findByUsuario(usuarioActual);

        TarjetaCredito tarjetaCredito = new TarjetaCredito();
        model.addAttribute("tarjetaCredito", tarjetaCredito);



        System.out.println("Ha salido del mostrarPagoReserva GET");

        return "pagoReserva";
    }

    @PostMapping("/pagarReserva")
    public String procesarPago(@ModelAttribute("tarjetaCredito") TarjetaCredito tarjetaCredito,
                               BindingResult bindingResult/*,
                               @RequestParam("reservaId") Long reservaId*/) {

        System.out.println("Ha entrado en el pagoReserva POST");

        if (bindingResult.hasErrors()) {
            System.out.println("Hay errores de validación");
            return "pagoReserva";
        }


        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuarioActual = usuarioRepository.findByCorreoElectronico(usernameEmail);

        TarjetaCredito tarjetaExistente = tarjetaCreditoRepository.findByUsuario(usuarioActual);

        if (tarjetaExistente != null) {
            tarjetaExistente.setTitularTarjeta(tarjetaCredito.getTitularTarjeta());
            tarjetaExistente.setNumeroTarjeta(tarjetaCredito.getNumeroTarjeta());
            tarjetaExistente.setMesVencimiento(tarjetaCredito.getMesVencimiento());
            tarjetaExistente.setAnoVencimiento(tarjetaCredito.getAnoVencimiento());
            tarjetaExistente.setCodigoSeguridad(tarjetaCredito.getCodigoSeguridad());
            tarjetaExistente.setRecordarTarj(tarjetaCredito.getRecordarTarj());
            tarjetaCreditoRepository.save(tarjetaExistente);
            System.out.println("Se ha guardado correctamente");
        } else {
            System.out.println("Es nulo");
            TarjetaCredito nuevaTarjeta = new TarjetaCredito();
            nuevaTarjeta.setTitularTarjeta(tarjetaCredito.getTitularTarjeta());
            nuevaTarjeta.setNumeroTarjeta(tarjetaCredito.getNumeroTarjeta());
            nuevaTarjeta.setMesVencimiento(tarjetaCredito.getMesVencimiento());
            nuevaTarjeta.setAnoVencimiento(tarjetaCredito.getAnoVencimiento());
            nuevaTarjeta.setCodigoSeguridad(tarjetaCredito.getCodigoSeguridad());
            nuevaTarjeta.setRecordarTarj(tarjetaCredito.getRecordarTarj());
            nuevaTarjeta.setUsuario(usuarioActual);


            tarjetaCreditoRepository.save(nuevaTarjeta);
            System.out.println("Se ha guardado correctamente");

        }

        /*Reserva reserva = reservaRepository.findById(Math.toIntExact(reservaId)).orElse(null);
        if (reserva != null) {
            reserva.setEstatus("PAGADO");
            reservaRepository.save(reserva);
        }*/

        return "redirect:/perfil";
    }

}
