package com.alura.cursos.ForoHub.repository;

import com.alura.cursos.ForoHub.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByCorreoElectronico(String usename);

}
