package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Bairro;

import java.util.Optional;

public interface BairroService extends CrudService<Bairro, Long> {
    Optional<Bairro> findById(Long id);

    Optional<Bairro> findByDescricao(String descricao);
}
