package com.snowshare.SnowShare.models;

import javax.persistence.*;

@Entity
@Table(name = "tarjeta_credito")
public class TarjetaCredito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tarjeta")
    private Long idTarjeta;

    @Column(name = "titularTarjeta")
    private String titularTarjeta;

    @Column(name = "numeroTarjeta")
    private String numeroTarjeta;

    @Column(name = "mesVencimiento")
    private String mesVencimiento;

    @Column(name = "anoVencimiento")
    private String anoVencimiento;

    @Column(name = "codigoSeguridad")
    private String codigoSeguridad;

    @Column(name = "recordarTarj")
    private Boolean recordarTarj;

    @OneToOne
    @JoinColumn(name="id_usuario", nullable=false)
    private Usuario usuario;

    public Long getIdTarjeta() {
        return idTarjeta;
    }

    public void setIdTarjeta(Long idTarjeta) {
        this.idTarjeta = idTarjeta;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }

    public void setTitularTarjeta(String titularTarjeta) {
        this.titularTarjeta = titularTarjeta;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getMesVencimiento() {
        return mesVencimiento;
    }

    public void setMesVencimiento(String mesVencimiento) {
        this.mesVencimiento = mesVencimiento;
    }

    public String getAnoVencimiento() {
        return anoVencimiento;
    }

    public void setAnoVencimiento(String anoVencimiento) {
        this.anoVencimiento = anoVencimiento;
    }

    public String getCodigoSeguridad() {
        return codigoSeguridad;
    }

    public void setCodigoSeguridad(String codigoSeguridad) {
        this.codigoSeguridad = codigoSeguridad;
    }

    public Boolean getRecordarTarj() {
        return recordarTarj;
    }

    public void setRecordarTarj(Boolean recordarTarj) {
        this.recordarTarj = recordarTarj;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
