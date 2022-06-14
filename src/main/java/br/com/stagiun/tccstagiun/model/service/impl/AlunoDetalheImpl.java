package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.domain.AlunoDetalhe;
import br.com.stagiun.tccstagiun.model.repository.AlunoDetalheRepository;
import br.com.stagiun.tccstagiun.model.service.AlunoDetalheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AlunoDetalheImpl implements AlunoDetalheService {

    @Autowired
    private AlunoDetalheRepository alunoDetalheRepository;

    @Override
    public AlunoDetalhe salvar(AlunoDetalhe alunoDetalhes) throws ResourceFoundException {
        Optional<AlunoDetalhe> existeAlunoDetalhes = findByAluno(alunoDetalhes.getAluno());

        if (existeAlunoDetalhes.isPresent()) {
            throw new ResourceFoundException("Aluno detalhes já encontrado!");
        }

        return alunoDetalheRepository.save(alunoDetalhes);
    }

    @Override
    public AlunoDetalhe editar(Long id, AlunoDetalhe alunoDetalhes) throws ResourceFoundException {
        Optional<AlunoDetalhe> existeAlunoDetalhes = findById(id);

        if (!existeAlunoDetalhes.isPresent()) {
            throw new ResourceFoundException("Aluno detalhes não encontrado!");
        }

        AlunoDetalhe updateAlunoDetalhes = existeAlunoDetalhes.get();
        updateAlunoDetalhes.update(id, alunoDetalhes);
        return alunoDetalheRepository.save(updateAlunoDetalhes);
    }

    @Override
    public List<AlunoDetalhe> list() {
        return alunoDetalheRepository.findAll();
    }

    @Override
    public Optional<AlunoDetalhe> findById(Long id) {
        return alunoDetalheRepository.findById(id);
    }

    @Override
    public Optional<AlunoDetalhe> findByAluno(Aluno aluno) {
        return alunoDetalheRepository.findByAluno(aluno);
    }

    @Override
    public void delete(Long id) {
        alunoDetalheRepository.deleteById(id);
    }
}


