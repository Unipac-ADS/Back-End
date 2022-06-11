package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Idioma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IdiomaRepository extends JpaRepository<Idioma, Long> {
    Optional<Idioma> findById(Long id);
    Optional<Idioma> findByNome(String nome);
}
