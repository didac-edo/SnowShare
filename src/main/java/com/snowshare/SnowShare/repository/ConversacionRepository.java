package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Conversacion;
import com.snowshare.SnowShare.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversacionRepository extends JpaRepository<Conversacion, Integer> {
    @Query("SELECT c FROM Conversacion c WHERE c.articulo.idArticulo = :articuloId")
    List<Conversacion> findAllByArticuloId(@Param("articuloId") Integer articuloId);

    List<Conversacion> findAllByPropietarioIdUsuario(Integer propietarioId);

    List<Conversacion> findAllByUsuarioIdUsuario(Integer usuarioId);

}
