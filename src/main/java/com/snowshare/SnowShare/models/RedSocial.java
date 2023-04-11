package com.snowshare.SnowShare.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "redsocial")
public class RedSocial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idRedSocial")
    private Integer idRedSocial;

    @Column(name = "redSocial")
    private String redSocial;

    @OneToMany(mappedBy = "redSocial", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Usuario> usuarios;


}
