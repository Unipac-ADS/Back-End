package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Cidade;

import java.util.Optional;

public interface CidadeService extends CrudService<Cidade, Long> {
    Optional<Cidade> findById(Long id);

    Optional<Cidade> findByDescricao(String descricap);
}
