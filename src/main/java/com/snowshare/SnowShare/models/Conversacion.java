package com.snowshare.SnowShare.models;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversacion")
public class Conversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idConversacion")
    private Integer idConversacion;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "idUsuario", referencedColumnName = "idUsuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "idPropietario", referencedColumnName = "idUsuario")
    private Usuario propietario;

    @ManyToOne
    @JoinColumn(name = "idArticulo", referencedColumnName = "idArticulo")
    private Articulo articulo;
}
