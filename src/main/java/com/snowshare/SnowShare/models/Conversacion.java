package com.snowshare.SnowShare.models;

import javax.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "conversacion")
public class Conversacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_conversacion")
    private Integer idConversacion;

    @Column(name = "mensaje")
    private String mensaje;

    @Column(name = "fechaHora")
    private LocalDateTime fechaHora;

    @ManyToOne
    @JoinColumn(name = "id_usuario", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_propietario", referencedColumnName = "id_usuario")
    private Usuario propietario;

    @ManyToOne
    @JoinColumn(name = "id_articulo", referencedColumnName = "id_articulo")
    private Articulo articulo;
}
