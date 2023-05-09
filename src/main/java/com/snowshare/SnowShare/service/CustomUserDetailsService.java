package com.snowshare.SnowShare.service;

import com.snowshare.SnowShare.models.Usuario;
import com.snowshare.SnowShare.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UsuarioRepository usuarioRepository;

    @Autowired
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String correoElectronico) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreoElectronico(correoElectronico);
        if (usuario == null) {
            throw new UsernameNotFoundException("No se encontró el usuario con correo electrónico: " + correoElectronico);
        }
        return new org.springframework.security.core.userdetails.User(usuario.getCorreoElectronico(), usuario.getContraseña(),
                new ArrayList<>());
    }

    public Usuario findByNombre(String nombre) {
        return usuarioRepository.findByNombre(nombre);
    }
}
