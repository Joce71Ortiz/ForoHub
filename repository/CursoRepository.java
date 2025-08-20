package com.alura.cursos.ForoHub.repository;

import com.alura.cursos.ForoHub.model.Curso;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {
}
