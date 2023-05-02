package com.snowshare.SnowShare.repository;

import com.snowshare.SnowShare.models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Usuario findByCorreoElectronico(String correoElectronico);

    Usuario findByNombre(String nombre);

}
