package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;
import com.snowshare.SnowShare.models.TarjetaCredito;
import com.snowshare.SnowShare.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TarjetaCreditoRepository extends JpaRepository<TarjetaCredito, Long> {

    TarjetaCredito findByUsuario(Usuario usuario);
}
