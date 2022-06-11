package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Perfil;

import java.util.Optional;

public interface PerfilService extends CrudService<Perfil, Long> {
    Optional<Perfil> findById(Long id);

    Optional<Perfil> findByDescricao(String descricao);
}
