package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.UsuarioPerfil;

import java.util.Optional;

public interface UsuarioPerfilService extends CrudService<UsuarioPerfil, Long> {
    Optional<UsuarioPerfil> findById(Long id);
}
