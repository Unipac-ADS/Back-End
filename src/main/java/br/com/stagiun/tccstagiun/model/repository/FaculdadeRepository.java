package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FaculdadeRepository extends JpaRepository<Faculdade, Long> {
    Optional<Faculdade> findById(Long id);
    Optional<Faculdade> findByNome(String nome);
}
