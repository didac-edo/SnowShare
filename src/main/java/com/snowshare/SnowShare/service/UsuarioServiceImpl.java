package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario getUsuarioActual() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        return usuarioRepository.findByCorreoElectronico(usernameEmail);
    }

    @Override
    public void actualizarNombre(String nuevoNombre) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreoElectronico(usernameEmail);
        System.out.println("El nombre usuario acutal es: " + usuario.getNombre());
        usuario.setNombre(nuevoNombre);
        System.out.println("El nuevo nombre es: " + nuevoNombre);
        usuarioRepository.save(usuario);
    }

    @Override
    public void cambiarFotoPerfil(byte[] fotoPerfil) {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String usernameEmail = userDetails.getUsername();
        Usuario usuario = usuarioRepository.findByCorreoElectronico(usernameEmail);
        System.out.println("EL nuevo fotoPerfil ha actualizar: " + Arrays.toString(fotoPerfil));
        usuario.setFotoPerfil(fotoPerfil);
        usuarioRepository.save(usuario);
    }
}
