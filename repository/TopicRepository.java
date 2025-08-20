package com.alura.cursos.ForoHub.repository;

import com.alura.cursos.ForoHub.model.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {

    Optional<Topic> findByTituloAndMensaje(String titulo, String mensaje);

    Page<Topic> findByCursoNombre(String nombre, Pageable pageable);
}
