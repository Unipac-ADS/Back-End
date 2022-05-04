package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.model.domain.Endereco;

import java.util.Optional;

public interface EnderecoService extends CrudService<Endereco, Long> {
    Optional<Endereco> findById(Long id);
}
