package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
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
    public AlunoDetalhe salvar(AlunoDetalhe alunoDetalhes) throws ResourceFoundException {
        Optional<AlunoDetalhe> existeAlunoDetalhes = findByAluno(alunoDetalhes.getAluno());

        if (existeAlunoDetalhes.isPresent()) {
            throw new ResourceFoundException("Aluno detalhes já encontrado!");
        }

        return alunoDetalhesRepository.save(alunoDetalhes);
    }

    @Override
    public AlunoDetalhe editar(Long id, AlunoDetalhe alunoDetalhes) throws ResourceFoundException {
        Optional<AlunoDetalhe> existeAlunoDetalhes = findById(id);

        if (!existeAlunoDetalhes.isPresent()) {
            throw new ResourceFoundException("Aluno detalhes não encontrado!");
        }

        AlunoDetalhe updateAlunoDetalhes = existeAlunoDetalhes.get();
        updateAlunoDetalhes.update(id, alunoDetalhes);
        return alunoDetalhesRepository.save(updateAlunoDetalhes);
    }

    @Override
    public List<AlunoDetalhe> list() {
        return alunoDetalhesRepository.findAll();
    }

    @Override
    public Optional<AlunoDetalhe> findById(Long id) {
        return alunoDetalhesRepository.findById(id);
    }

    @Override
    public Optional<AlunoDetalhe> findByAluno(Aluno aluno) {
        return alunoDetalhesRepository.findByAluno(aluno);
    }

    @Override
    public void delete(Long id) {
        alunoDetalhesRepository.deleteById(id);
    }
}


