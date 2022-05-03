package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Aluno;

import java.util.Optional;

public interface AlunoService extends CrudService<Aluno, Long> {
    Optional<Aluno> findByCpf(Integer cpf);
}
