package br.com.stagiun.tccstagiun.model.repository;

import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.domain.Empresa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpresaRepository extends JpaRepository<Empresa, Long> {
    Optional<Empresa> findByCnpj(Integer cnpj);
}
