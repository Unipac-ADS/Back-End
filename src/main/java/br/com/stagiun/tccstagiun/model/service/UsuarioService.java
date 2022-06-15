package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceNotFoundException;
import br.com.stagiun.tccstagiun.filter.TokenResponse;
import br.com.stagiun.tccstagiun.model.domain.Usuario;

import java.util.Optional;

public interface UsuarioService extends CrudService<Usuario, Long> {
    Optional<Usuario> findById(Long id);

    Optional<Usuario> findByNome(String nome);

    TokenResponse getLoginAndReturnToken(Usuario usuario) throws ResourceNotFoundException;
}
