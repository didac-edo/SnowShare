package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenArticuloRepository extends JpaRepository<ImagenArticulo, Integer> {
    List<ImagenArticulo> findByArticulo(Articulo articulo);

    @Query("SELECT ia FROM ImagenArticulo ia WHERE ia.articulo.propietario.idUsuario = :userId AND ia.idImagenArticulo IN (SELECT MIN(i.idImagenArticulo) FROM ImagenArticulo i GROUP BY i.articulo.idArticulo)")
    List<ImagenArticulo> findFirstImagesByUser(@Param("userId") Integer userId);

    void deleteByArticuloIdArticulo(Integer articuloId);

    List<ImagenArticulo> findByArticuloIdArticulo(Integer IdArticulo);

}

