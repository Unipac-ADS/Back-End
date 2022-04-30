package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Pais;
import br.com.stagiun.tccstagiun.model.repository.PaisRepository;
import br.com.stagiun.tccstagiun.model.service.PaisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaisServiceImpl implements PaisService {

    @Autowired
    private PaisRepository paisRepository;

    @Override
    public Pais salvar(Pais pais) {
        return paisRepository.save(pais);
    }

    @Override
    public Pais editar(Long id, Pais pais) {
        pais.setId(id);
        return paisRepository.save(pais);
    }

    @Override
    public List<Pais> list() {
        return paisRepository.findAll();
    }

    @Override
    public Optional<Pais> findById(Long id) {
        return paisRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        paisRepository.deleteById(id);
    }
}
