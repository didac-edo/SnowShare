package com.snowshare.SnowShare.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUsuario")
    private Integer idUsuario;

    @Column(name = "nombre")
    private String nombre;

    @Column(name = "correoElectronico")
    private String correoElectronico;

    @Column(name = "contraseña")
    private String contraseña;

    @ManyToOne
    @JoinColumn(name = "idRedSocial", referencedColumnName = "idRedSocial")
    private RedSocial redSocial;

    @OneToMany(mappedBy = "propietario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Articulo> articulos;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Resena> resenas;
}
