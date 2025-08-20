package com.alura.cursos.ForoHub.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

import com.alura.cursos.ForoHub.dto.TopicCreateDTO;
import com.alura.cursos.ForoHub.model.Curso;
import com.alura.cursos.ForoHub.model.Topic;
import com.alura.cursos.ForoHub.model.Usuario;
import com.alura.cursos.ForoHub.repository.CursoRepository;
import com.alura.cursos.ForoHub.repository.TopicRepository;
import com.alura.cursos.ForoHub.repository.UsuarioRepository;

@RestController
@RequestMapping("/topics")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @GetMapping
    public ResponseEntity<List<Topic>> getAllTopics() {
        List<Topic> topics = topicRepository.findAll();
        return ResponseEntity.ok(topics);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Topic> getTopicById(@PathVariable Long id) {
        // Busca un tópico por su ID en la base de datos
        Optional<Topic> topic = topicRepository.findById(id);

        // Verifica si el tópico se encontró
        if (topic.isPresent()) {
            // Si existe, devuelve una respuesta 200 OK con el objeto tópico
            return ResponseEntity.ok(topic.get());
        } else {
            // Si no se encuentra, devuelve una respuesta 404 Not Found
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createTopic(@RequestBody @Valid TopicCreateDTO topicCreateDTO) {
        // Regla de negocio: Verificar si ya existe un tópico con el mismo título y mensaje
        Optional<Topic> existingTopic = topicRepository.findByTituloAndMensaje(
                topicCreateDTO.getTitulo(),
                topicCreateDTO.getMensaje()
        );

        if (existingTopic.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Ya existe un tópico con el mismo título y mensaje.");
        }

        // Buscar el usuario y el curso por sus IDs
        Optional<Usuario> autorOptional = usuarioRepository.findById(topicCreateDTO.getAutorId());
        Optional<Curso> cursoOptional = cursoRepository.findById(topicCreateDTO.getCursoId());

        // Regla de negocio: Validar si el autor o el curso existen
        if (autorOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor no encontrado.");
        }
        if (cursoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Curso no encontrado.");
        }

        Topic newTopic = new Topic(
                topicCreateDTO.getTitulo(),
                topicCreateDTO.getMensaje(),
                autorOptional.get(),
                cursoOptional.get()
        );

        // Guardar el tópico y devolver una respuesta
        Topic savedTopic = topicRepository.save(newTopic);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedTopic);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTopic(@PathVariable Long id, @RequestBody @Valid TopicCreateDTO topicCreateDTO) {

        // 1. Verificar si el tópico a actualizar existe
        Optional<Topic> topicOptional = topicRepository.findById(id);
        if (topicOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 2. Regla de negocio: Verificar si el nuevo título y mensaje ya existen en otro tópico
        Optional<Topic> existingTopic = topicRepository.findByTituloAndMensaje(
                topicCreateDTO.getTitulo(),
                topicCreateDTO.getMensaje()
        );

        if (existingTopic.isPresent() && !existingTopic.get().getId().equals(id)) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Ya existe un tópico con el mismo título y mensaje.");
        }

        // 3. Obtener el tópico de la base de datos
        Topic topic = topicOptional.get();

        // 4. Actualizar los campos del tópico
        topic.setTitulo(topicCreateDTO.getTitulo());
        topic.setMensaje(topicCreateDTO.getMensaje());

        // Buscar y actualizar el autor y curso, si es necesario
        Optional<Usuario> autorOptional = usuarioRepository.findById(topicCreateDTO.getAutorId());
        Optional<Curso> cursoOptional = cursoRepository.findById(topicCreateDTO.getCursoId());

        if (autorOptional.isEmpty() || cursoOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Autor o curso no encontrado.");
        }

        topic.setAutor(autorOptional.get());
        topic.setCurso(cursoOptional.get());

        // 5. Guardar el tópico actualizado
        Topic updatedTopic = topicRepository.save(topic);

        return ResponseEntity.ok(updatedTopic);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTopic(@PathVariable Long id) {
        // 1. Verificar si el tópico con el ID dado existe en la base de datos.
        if (topicRepository.existsById(id)) {
            // 2. Si existe, lo eliminamos utilizando el método deleteById de JpaRepository.
            topicRepository.deleteById(id);
            // 3. Devolvemos una respuesta 204 No Content, que es el estándar para una eliminación exitosa sin cuerpo de respuesta.
            return ResponseEntity.noContent().build();
        } else {
            // 4. Si el tópico no existe, devolvemos una respuesta 404 Not Found.
            return ResponseEntity.notFound().build();
        }
    }
}
