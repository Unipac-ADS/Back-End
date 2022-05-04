package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;

import java.util.Optional;

public interface AcompanhamentoService extends CrudService<Acompanhamento, Long> {
    Optional<Acompanhamento> findById(Long id);
}
