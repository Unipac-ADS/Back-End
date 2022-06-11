package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoDetalhesRepository extends JpaRepository<AlunoDetalhes, Long> {
    Optional<AlunoDetalhes> findById(Long id);
    Optional<AlunoDetalhes> findByAluno(Aluno aluno);

}
