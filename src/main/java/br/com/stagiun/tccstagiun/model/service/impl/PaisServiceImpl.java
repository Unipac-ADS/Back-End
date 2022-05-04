package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
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
    public Pais salvar(Pais pais) throws ResourceFoundException {
        Optional<Pais> existePais = findById(pais.getId());

        if (existePais.isPresent()) {
            throw new ResourceFoundException("País já encontrado!");
        }

        return paisRepository.save(pais);
    }

    @Override
    public Pais editar(Long id, Pais pais) throws ResourceFoundException{
        Optional<Pais> existePais = findById(id);

        if (!existePais.isPresent()) {
            throw new ResourceFoundException("País não encontrado!");
        }

        Pais updatePais = existePais.get();
        updatePais.update(id, pais);
        return paisRepository.save(updatePais);
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
