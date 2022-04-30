package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhes;
import br.com.stagiun.tccstagiun.model.repository.AlunoDetalhesRepository;
import br.com.stagiun.tccstagiun.model.service.AlunoDetalhesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AlunoDetalhesImpl implements AlunoDetalhesService {

    @Autowired
    private AlunoDetalhesRepository alunoDetalhesRepository;

    @Override
    public AlunoDetalhes salvar(AlunoDetalhes alunoDetalhes) {
        return alunoDetalhesRepository.save(alunoDetalhes);
    }

    @Override
    public AlunoDetalhes editar(Long id, AlunoDetalhes alunoDetalhes) {
        alunoDetalhes.setId(id);
        return alunoDetalhesRepository.save(alunoDetalhes);
    }

    @Override
    public List<AlunoDetalhes> list() {
        return alunoDetalhesRepository.findAll();
    }

    @Override
    public Optional<AlunoDetalhes> findById(Long id) {
        return alunoDetalhesRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        alunoDetalhesRepository.deleteById(id);
    }
}


