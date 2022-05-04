package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.AplicacaoAlunoVaga;
import br.com.stagiun.tccstagiun.model.repository.AplicacaoAlunoVagaRepository;
import br.com.stagiun.tccstagiun.model.service.AplicacaoAlunoVagaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AplicacaoAlunoVagaImpl implements AplicacaoAlunoVagaService {

    @Autowired
    private AplicacaoAlunoVagaRepository aplicacaoAlunoVagaRepository;

    @Override
    public AplicacaoAlunoVaga salvar(AplicacaoAlunoVaga aplicacaoAlunoVaga) throws ResourceFoundException {
        Optional<AplicacaoAlunoVaga> existeAplicacaoAlunoVaga = findById(aplicacaoAlunoVaga.getId());

        if (existeAplicacaoAlunoVaga.isPresent()) {
            throw new ResourceFoundException("Aplicação para vaga já encontrada!");
        }

        return aplicacaoAlunoVagaRepository.save(aplicacaoAlunoVaga);
    }

    @Override
    public AplicacaoAlunoVaga editar(Long id, AplicacaoAlunoVaga aplicacaoAlunoVaga) throws ResourceFoundException {
        Optional<AplicacaoAlunoVaga> existeAplicacaoAlunoVaga = findById(id);

        if (!existeAplicacaoAlunoVaga.isPresent()) {
            throw new ResourceFoundException("Aplicação para vaga não encontrada!");
        }

        AplicacaoAlunoVaga updateAplicacaoAlunoVaga = existeAplicacaoAlunoVaga.get();
        updateAplicacaoAlunoVaga.update(id, aplicacaoAlunoVaga);
        return aplicacaoAlunoVagaRepository.save(updateAplicacaoAlunoVaga);
    }

    @Override
    public List<AplicacaoAlunoVaga> list() {
        return aplicacaoAlunoVagaRepository.findAll();
    }

    @Override
    public Optional<AplicacaoAlunoVaga> findById(Long id) {
        return aplicacaoAlunoVagaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        aplicacaoAlunoVagaRepository.deleteById(id);
    }
}

