package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhes;

import java.util.Optional;

public interface AlunoDetalhesService extends CrudService<AlunoDetalhes, Long> {
    Optional<AlunoDetalhes> findById(Long id);
}
