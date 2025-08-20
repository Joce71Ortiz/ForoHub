package com.alura.cursos.ForoHub.dto;

import jakarta.validation.constraints.NotBlank;

public record AuthDTO(@NotBlank
                      String correo_electronico,

                      @NotBlank
                      String contrasena) {
}
