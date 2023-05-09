package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.models.Articulo;
import com.snowshare.SnowShare.models.ImagenArticulo;
import com.snowshare.SnowShare.repository.ImagenArticuloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagenArticuloServiceImpl implements ImagenArticuloService {

    @Autowired
    private ImagenArticuloRepository imagenArticuloRepository;

    @Override
    public List<ImagenArticulo> getImagenesByArticulo(Articulo articulo) {
        return imagenArticuloRepository.findByArticulo(articulo);
    }
}
