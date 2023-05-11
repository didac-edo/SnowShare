package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservaRepository extends JpaRepository<Reserva, Integer> {

    List<Reserva> findByArticuloIdArticulo(Integer idArticulo);

    @Query("SELECT r FROM Reserva r WHERE r.articulo.idArticulo = :idArticulo AND " +
            "(r.fechaInicio <= :fechaFin AND r.fechaFin >= :fechaInicio)")
    List<Reserva> findOverlappingReservations(@Param("idArticulo") Integer idArticulo,
                                              @Param("fechaInicio") LocalDate fechaInicio,
                                              @Param("fechaFin") LocalDate fechaFin);

    List<Reserva> findByUsuarioIdUsuario(Integer idUsuario);

}
