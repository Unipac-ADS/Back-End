package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlunoDetalhesRepository extends JpaRepository<AlunoDetalhe, Long> {
    Optional<AlunoDetalhe> findById(Long id);
    Optional<AlunoDetalhe> findByAluno(Aluno aluno);

}
