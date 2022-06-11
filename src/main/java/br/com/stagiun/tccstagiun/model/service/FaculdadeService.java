package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Faculdade;

import java.util.Optional;

public interface FaculdadeService extends CrudService<Faculdade, Long> {
    Optional<Faculdade> findById(Long id);

    Optional<Faculdade> findByNome(String nome);
}
