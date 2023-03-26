package com.snowshare.SnowShare.models;

import jakarta.persistence.*;

@Entity
@Table(name = "articuloporcategoria")
public class ArticuloPorCategoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProductoCategoria")
    private Integer idProductoCategoria;

    @ManyToOne
    @JoinColumn(name = "idArticulo", referencedColumnName = "idArticulo")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "idCategoria", referencedColumnName = "idCategoria")
    private Categoria categoria;

    public Integer getIdProductoCategoria() {
        return idProductoCategoria;
    }

    public void setIdProductoCategoria(Integer idProductoCategoria) {
        this.idProductoCategoria = idProductoCategoria;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
