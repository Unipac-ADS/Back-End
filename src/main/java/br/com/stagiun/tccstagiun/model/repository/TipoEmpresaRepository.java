package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TipoEmpresaRepository extends JpaRepository<TipoEmpresa, Long> {
    Optional<TipoEmpresa> findById(Long id);
    Optional<TipoEmpresa> findByDescricao(String descricao);


}
