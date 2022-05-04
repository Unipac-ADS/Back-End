package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Pais;

import java.util.Optional;

public interface PaisService extends CrudService<Pais, Long> {
    Optional<Pais> findById(Long id);
}
