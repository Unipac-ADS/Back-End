package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
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
    public Cep salvar(Cep cep) throws ResourceFoundException {
        Optional<Cep> existeCep = findByDescricao(cep.getDescricao());

        if (existeCep.isPresent()) {
            throw new ResourceFoundException("CEP já encontrado!");
        }

        return cepRepository.save(cep);
    }

    @Override
    public Cep editar(Long id, Cep cep) throws ResourceFoundException {
        Optional<Cep> existeCep = findById(id);

        if (!existeCep.isPresent()) {
            throw new ResourceFoundException("CEP não encontrado!");
        }

        Cep updateCep = existeCep.get();
        updateCep.update(id, cep);
        return cepRepository.save(updateCep);
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
    public Optional<Cep> findByDescricao(String descricao) {
        return cepRepository.findByDescricao(descricao);
    }

    @Override
    public void delete(Long id) {
        cepRepository.deleteById(id);
    }
}
