package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Aluno;
import br.com.stagiun.tccstagiun.model.repository.AlunoRepository;
import br.com.stagiun.tccstagiun.model.service.AlunoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AlunoServiceImpl implements AlunoService {

    @Autowired
    private AlunoRepository alunoRepository;

    @Override
    public Aluno salvar(Aluno aluno) throws ResourceFoundException {
        Optional<Aluno> existeAluno = findByCpf(aluno.getCpf());
        if (!existeAluno.isPresent()) {
            throw new ResourceFoundException("Aluno jã encontrado!");
        }

        return alunoRepository.save(aluno);
    }

    @Override
    public Aluno editar(Long id, Aluno aluno) throws ResourceFoundException {
        Optional<Aluno> existeAluno = findById(id);
        if (!existeAluno.isPresent()) {
            throw new ResourceFoundException("Aluno jã encontrado!");
        }
        Aluno alunoUpdate = existeAluno.get();
        alunoUpdate.update(id, aluno);
        return alunoRepository.save(alunoUpdate);
    }

    @Override
    public List<Aluno> list() {
        return alunoRepository.findAll();
    }

    @Override
    public Optional<Aluno> findById(Long id) {
        return alunoRepository.findById(id);
    }

    @Override
    public Optional<Aluno> findByCpf(Integer cpf) {
        return alunoRepository.findByCpf(cpf);
    }

    @Override
    public void delete(Long id) {
        alunoRepository.deleteById(id);
    }
}
