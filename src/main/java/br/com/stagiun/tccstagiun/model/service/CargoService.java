package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Cargo;

import java.util.Optional;

public interface CargoService extends CrudService<Cargo, Long> {
    Optional<Cargo> findById(Long id);
}
