package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Cep;

import java.util.Optional;

public interface CepService extends CrudService<Cep, Long> {
    Optional<Cep> findById(Long id);
}
