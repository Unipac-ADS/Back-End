package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Empresa;

import java.util.Optional;

public interface EmpresaService extends CrudService<Empresa, Long> {
    Optional<Empresa> findByCnpj(Long cnpj);
}
