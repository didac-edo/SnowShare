package com.snowshare.SnowShare.models;

public class ImagenDto {
    private String imagenBase64;
    private Integer idImagenArticulo;

    public String getImagenBase64() {
        return imagenBase64;
    }

    public void setImagenBase64(String imagenBase64) {
        this.imagenBase64 = imagenBase64;
    }

    public Integer getIdImagenArticulo() {
        return idImagenArticulo;
    }

    public void setIdImagenArticulo(Integer idImagenArticulo) {
        this.idImagenArticulo = idImagenArticulo;
    }
}
