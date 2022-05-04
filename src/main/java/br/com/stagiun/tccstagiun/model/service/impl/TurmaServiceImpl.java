package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Turma;
import br.com.stagiun.tccstagiun.model.repository.TurmaRepository;
import br.com.stagiun.tccstagiun.model.service.TurmaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaServiceImpl implements TurmaService {

    @Autowired
    private TurmaRepository turmaRepository;

    @Override
    public Turma salvar(Turma turma) throws ResourceFoundException {
        Optional<Turma> existeTurma = findById(turma.getId());

        if (existeTurma.isPresent()) {
            throw new ResourceFoundException("Turma já encontrada!");
        }

        return turmaRepository.save(turma);
    }

    @Override
    public Turma editar(Long id, Turma turma) throws ResourceFoundException{
        Optional<Turma> existeTurma = findById(id);

        if (!existeTurma.isPresent()) {
            throw new ResourceFoundException("Turma não encontrada!");
        }

        Turma updateTurma = existeTurma.get();
        updateTurma.update(id, turma);
        return turmaRepository.save(updateTurma);
    }

    @Override
    public List<Turma> list() {
        return turmaRepository.findAll();
    }

    @Override
    public Optional<Turma> findById(Long id) {
        return turmaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        turmaRepository.deleteById(id);
    }
}
