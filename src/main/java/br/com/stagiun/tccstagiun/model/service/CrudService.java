package br.com.stagiun.tccstagiun.model.service;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CrudService<T, ID extends Serializable> {
    T salvar(T t);
    T editar(ID id, T t);
    List<T> list();
    Optional<T> findById(ID id);
    void delete(Long id);
}
