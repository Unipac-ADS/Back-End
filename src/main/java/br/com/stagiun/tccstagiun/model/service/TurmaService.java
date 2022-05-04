package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Turma;

import java.util.Optional;

public interface TurmaService extends CrudService<Turma, Long> {
    Optional<Turma> findById(Long id);
}
