package com.snowshare.SnowShare.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idArticulo")
    private Integer idArticulo;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "marca")
    private String marca;

    @Column(name = "modelo")
    private String modelo;

    @Column(name = "precio")
    private BigDecimal precio;

    @Column(name = "imagen")
    private String imagen;

    //hola
    @ManyToOne
    @JoinColumn(name = "idPropietario", referencedColumnName = "idUsuario")
    private Usuario propietario;

    @OneToMany(mappedBy = "articulo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Resena> resenas;

}
