package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;

import java.util.Optional;

public interface TipoEmpresaService extends CrudService<TipoEmpresa, Long> {
    Optional<TipoEmpresa> findById(Long id);

    Optional<TipoEmpresa> findByDescricao(String descricao);
}
