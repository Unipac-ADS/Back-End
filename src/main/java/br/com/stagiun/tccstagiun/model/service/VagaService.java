package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Vaga;

import java.util.Optional;

public interface VagaService extends CrudService<Vaga, Long> {
    Optional<Vaga> findById(Long id);

    Optional<Vaga> findByNome(String nome);
}
