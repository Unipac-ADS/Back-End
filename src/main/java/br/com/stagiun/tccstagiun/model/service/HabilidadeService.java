package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Habilidade;

import java.util.Optional;

public interface HabilidadeService extends CrudService<Habilidade, Long> {
    Optional<Habilidade> findById(Long id);

    Optional<Habilidade> findByNome(String nome);
}
