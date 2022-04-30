package br.com.stagiun.tccstagiun.model.service.impl;

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
    public Acompanhamento salvar(Acompanhamento acompanhamento) {
        return acompanhamentoRepository.save(acompanhamento);
    }

    @Override
    public Acompanhamento editar(Long id, Acompanhamento acompanhamento) {
        acompanhamento.setId(id);
        return acompanhamentoRepository.save(acompanhamento);
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

