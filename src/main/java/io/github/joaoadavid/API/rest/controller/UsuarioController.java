package io.github.joaoadavid.API.rest.controller;

import io.github.joaoadavid.API.domain.entity.Usuario;
import io.github.joaoadavid.API.exception.SenhaInvalidaException;
import io.github.joaoadavid.API.rest.dto.CredenciaisDTO;
import io.github.joaoadavid.API.rest.dto.TokenDTO;
import io.github.joaoadavid.API.security.jwt.JwtService;
import io.github.joaoadavid.API.service.impl.UsuarioServiceImpl;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    @ApiOperation(("Cria um novo usuario"))
    @ApiResponses({@ApiResponse(code = 200, message = "Usuario criado com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possivel criar o usuario")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario salvar (@RequestBody @Valid Usuario usuario){
        String senhaCripografada = passwordEncoder.encode(usuario.getSenha());
        usuario.setSenha(senhaCripografada);
        return usuarioService.salvar(usuario);
    }

    @ApiOperation(("Login/autenticação do usuario"))
    @ApiResponses({@ApiResponse(code = 200, message = "Login realizado com sucesso"),
            @ApiResponse(code = 404, message = "Não foi possivel efetuar o login")})
    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredenciaisDTO credenciaisDTO){
        try {
            Usuario usuario = Usuario.builder()
                    .login(credenciaisDTO.getLogin())
                    .senha(credenciaisDTO.getSenha())
                    .build();

           UserDetails usuarioAutenticado =  usuarioService.autenticar(usuario);
           String token =jwtService.gerarToken(usuario);
           return new TokenDTO(usuario.getLogin(), token);

        }catch (UsernameNotFoundException| SenhaInvalidaException e){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

    }
}

}
