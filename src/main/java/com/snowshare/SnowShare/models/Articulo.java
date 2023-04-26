package com.snowshare.SnowShare.models;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Set;

@Entity
@Table(name = "articulo")
public class Articulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_articulo")
    private Integer idArticulo;

    @Column(name = "titulo")
    private String titulo;

    @Column(name = "descripcion")
    private String descripcion;

    @Column(name = "codigoPostal")
    private Integer codigoPostal;

    @Column(name = "precioDia")
    private BigDecimal precioDia;

    @Column(name = "diasMinimo")
    private Integer diasMinimo;

    @Column(name = "descuentoPrecio")
    private Integer descuentoPrecio;

    @ManyToOne
    @JoinColumn(name = "id_propietario", referencedColumnName = "Id_usuario")
    private Usuario propietario;

    @ManyToOne
    @JoinColumn(name = "id_categoria", referencedColumnName = "id_categoria")
    private Categoria categoria;

    public Integer getIdArticulo() {
        return idArticulo;
    }

    public void setIdArticulo(Integer idArticulo) {
        this.idArticulo = idArticulo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Integer getCodigoPostal() {
        return codigoPostal;
    }

    public void setCodigoPostal(Integer codigoPostal) {
        this.codigoPostal = codigoPostal;
    }

    public BigDecimal getPrecioDia() {
        return precioDia;
    }

    public void setPrecioDia(BigDecimal precioDia) {
        this.precioDia = precioDia;
    }

    public Integer getDiasMinimo() {
        return diasMinimo;
    }

    public void setDiasMinimo(Integer diasMinimo) {
        this.diasMinimo = diasMinimo;
    }

    public Integer getDescuentoPrecio() {
        return descuentoPrecio;
    }

    public void setDescuentoPrecio(Integer descuentoPrecio) {
        this.descuentoPrecio = descuentoPrecio;
    }

    public Usuario getPropietario() {
        return propietario;
    }

    public void setPropietario(Usuario propietario) {
        this.propietario = propietario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}
