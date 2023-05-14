package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.Resena;
import com.snowshare.SnowShare.models.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResenaRepository extends JpaRepository<Resena, Integer> {
    List<Resena> findByArticulo(Articulo articulo);
    List<Resena> findByArticuloIdArticulo(Integer idArticulo);
}
