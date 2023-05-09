package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;

import java.util.List;

public interface ImagenArticuloService {
    List<ImagenArticulo> getImagenesByArticulo(Articulo articulo);
}

