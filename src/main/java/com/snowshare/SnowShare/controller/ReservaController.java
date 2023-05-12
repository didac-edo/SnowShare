package com.snowshare.SnowShare.controller;

import com.snowshare.SnowShare.repository.ReservaRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ReservaController {
    private final ReservaRepository reservaRepository;

    public ReservaController(ReservaRepository reservaRepository) {
        this.reservaRepository = reservaRepository;
    }

    @GetMapping("/api/articulos/{idArticulo}/reservas")
    public List<ReservaDto> getReservasPorArticulo(@PathVariable Integer idArticulo) {
        System.out.println("Entrando en getReservasPorArticulo con idArticulo: " + idArticulo);
        List<ReservaDto> reservas = reservaRepository.findByArticuloIdArticulo(idArticulo)
                .stream()
                .map(reserva -> new ReservaDto(reserva.getFechaInicio(), reserva.getFechaFin()))
                .collect(Collectors.toList());
        System.out.println("Saliendo de getReservasPorArticulo con reservas: " + reservas);
        return reservas;
    }

    public static class ReservaDto {

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fechaInicio;

        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
        private LocalDate fechaFin;

        public ReservaDto(LocalDate fechaInicio, LocalDate fechaFin) {
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }

        public LocalDate getFechaInicio() {
            return fechaInicio;
        }

        public LocalDate getFechaFin() {
            return fechaFin;
        }
    }
}
