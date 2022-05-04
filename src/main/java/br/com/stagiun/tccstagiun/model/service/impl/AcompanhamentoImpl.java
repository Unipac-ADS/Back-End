package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Acompanhamento;
import br.com.stagiun.tccstagiun.model.repository.AcompanhamentoRepository;
import br.com.stagiun.tccstagiun.model.service.AcompanhamentoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AcompanhamentoImpl implements AcompanhamentoService {

    @Autowired
    private AcompanhamentoRepository acompanhamentoRepository;

    @Override
    public Acompanhamento salvar(Acompanhamento acompanhamento) throws ResourceFoundException {
        Optional<Acompanhamento> existeAcompanhamento = findById(acompanhamento.getId());

        if(existeAcompanhamento.isPresent()) {
            throw new ResourceFoundException("Acompanhamento já encontrado!");
        }

        return acompanhamentoRepository.save(acompanhamento);
    }

    @Override
    public Acompanhamento editar(Long id, Acompanhamento acompanhamento) throws ResourceFoundException{
        Optional<Acompanhamento> existeAcompanhamento = findById(id);

        if(!existeAcompanhamento.isPresent()) {
            throw new ResourceFoundException("Acompanhamento não encontrado!");
        }

        Acompanhamento updateAcompanhamento = existeAcompanhamento.get();
        updateAcompanhamento.update(id, acompanhamento);
        return acompanhamentoRepository.save(updateAcompanhamento);
    }

    @Override
    public List<Acompanhamento> list() {
        return acompanhamentoRepository.findAll();
    }

    @Override
    public Optional<Acompanhamento> findById(Long id) {
        return acompanhamentoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        acompanhamentoRepository.deleteById(id);
    }
}

