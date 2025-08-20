package com.alura.cursos.ForoHub.controller;

import com.alura.cursos.ForoHub.dto.AuthDTO;
import com.alura.cursos.ForoHub.dto.AuthTokenDTO;
import com.alura.cursos.ForoHub.model.TokenService;
import com.alura.cursos.ForoHub.model.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<AuthTokenDTO> authenticateUser(@RequestBody @Valid AuthDTO authDTO) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authDTO.correo_electronico(),
                authDTO.contrasena()
        );

        var authenticatedUser = authenticationManager.authenticate(authToken);
        var token = tokenService.generateToken((Usuario) authenticatedUser.getPrincipal());

        return ResponseEntity.ok(new AuthTokenDTO(token));
    }
}
