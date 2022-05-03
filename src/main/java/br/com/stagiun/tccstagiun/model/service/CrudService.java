package br.com.stagiun.tccstagiun.model.service;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID extends Serializable> {
    T salvar(T t) throws ResourceFoundException;
    T editar(ID id, T t) throws ResourceFoundException;
    List<T> list();
    Optional<T> findById(ID id);
    void delete(Long id);
}
