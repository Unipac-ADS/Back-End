package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;

import java.util.Optional;

public interface AlunoDetalheService extends CrudService<AlunoDetalhe, Long> {
    Optional<AlunoDetalhe> findById(Long id);

    Optional<AlunoDetalhe> findByAluno(Aluno aluno);
}
