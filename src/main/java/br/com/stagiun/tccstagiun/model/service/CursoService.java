package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Curso;

import java.util.Optional;

public interface CursoService extends CrudService<Curso, Long> {
    Optional<Curso> findById(Long id);
}
