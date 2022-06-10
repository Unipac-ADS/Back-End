package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findById(Long id);
    Optional<Usuario> findByNome(String nome);
}
