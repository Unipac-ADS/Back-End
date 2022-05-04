package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Dica;

import java.util.Optional;

public interface DicaService extends CrudService<Dica, Long> {
    Optional<Dica> findById(Long id);
}
