package com.snowshare.SnowShare.models;

public class ChatInfo {
    private Usuario usuario;
    private Articulo articulo;

    public ChatInfo(Usuario usuario, Articulo articulo) {
        this.usuario = usuario;
        this.articulo = articulo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public void setArticulo(Articulo articulo) {
        this.articulo = articulo;
    }
}
