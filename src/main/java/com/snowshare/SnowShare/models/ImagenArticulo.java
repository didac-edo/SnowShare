package com.snowshare.SnowShare.models;

import javax.persistence.*;

@Entity
@Table(name = "imagen_articulo")
public class ImagenArticulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_imagen_articulo")
    private Integer idImagenArticulo;

    @Column(name = "imagen")
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id_articulo")
    private Articulo articulo;

    public Integer getIdImagenArticulo() {
        return idImagenArticulo;
    }

    public void setIdImagenArticulo(Integer idImagenArticulo) {
        this.idImagenArticulo = idImagenArticulo;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
