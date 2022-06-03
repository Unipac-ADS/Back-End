package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Estado;

import java.util.Optional;

public interface EstadoService extends CrudService<Estado, Long> {
    Optional<Estado> findById(Long id);
    Optional<Estado> findByDescricao(String descricao);
}
