package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImagenArticuloRepository extends JpaRepository<ImagenArticulo, Integer> {
    List<ImagenArticulo> findByArticulo(Articulo articulo);

}

