package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.models.Usuario;

public interface UsuarioService {
    Usuario getUsuarioActual();

    void actualizarNombre(String nuevoNombre);

    void cambiarFotoPerfil(byte[] fotoPerfil);
}
