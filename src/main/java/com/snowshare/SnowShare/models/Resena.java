package com.snowshare.SnowShare.models;

import jakarta.persistence.*;

@Entity
@Table(name = "resenas")
public class Resena {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idComentario")
    private Integer idComentario;

    @Column(name = "comentario")
    private String comentario;

    @Column(name = "calificacion")
    private Integer calificacion;

    @ManyToOne
    @JoinColumn(name = "idArticulo", referencedColumnName = "idArticulo")
    private Articulo articulo;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;

}
