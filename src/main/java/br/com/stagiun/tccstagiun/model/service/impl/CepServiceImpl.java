package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Cep;
import br.com.stagiun.tccstagiun.model.repository.CepRepository;
import br.com.stagiun.tccstagiun.model.service.CepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CepServiceImpl implements CepService {

    @Autowired
    private CepRepository cepRepository;

    @Override
    public Cep salvar(Cep cep) {
        return cepRepository.save(cep);
    }

    @Override
    public Cep editar(Long id, Cep cep) {
        cep.setId(id);
        return cepRepository.save(cep);
    }

    @Override
    public List<Cep> list() {
        return cepRepository.findAll();
    }

    @Override
    public Optional<Cep> findById(Long id) {
        return cepRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        cepRepository.deleteById(id);
    }
}
