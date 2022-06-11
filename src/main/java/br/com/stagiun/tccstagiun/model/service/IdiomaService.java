package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Idioma;

import java.util.Optional;

public interface IdiomaService extends CrudService<Idioma, Long> {
    Optional<Idioma> findById(Long id);

    Optional<Idioma> findByNome(String nome);
}
