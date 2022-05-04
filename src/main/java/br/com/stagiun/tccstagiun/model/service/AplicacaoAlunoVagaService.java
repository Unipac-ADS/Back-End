package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;

import java.util.Optional;

public interface AplicacaoAlunoVagaService extends CrudService<AplicacaoAlunoVaga, Long> {
    Optional<AplicacaoAlunoVaga> findById(Long id);
}
