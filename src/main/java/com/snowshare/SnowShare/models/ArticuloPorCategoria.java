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


}
