package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AplicacaoAlunoVagaRepository extends JpaRepository<AplicacaoAlunoVaga, Long> {
    Optional<AplicacaoAlunoVaga> findById(Long id);
}
