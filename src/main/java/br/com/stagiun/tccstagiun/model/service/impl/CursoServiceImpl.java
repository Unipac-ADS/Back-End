package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Curso;
import br.com.stagiun.tccstagiun.model.repository.CursoRepository;
import br.com.stagiun.tccstagiun.model.service.CursoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CursoServiceImpl implements CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Override
    public Curso salvar(Curso curso) {
        return cursoRepository.save(curso);
    }

    @Override
    public Curso editar(Long id, Curso curso) {
        curso.setId(id);
        return cursoRepository.save(curso);
    }

    @Override
    public List<Curso> list() {
        return cursoRepository.findAll();
    }

    @Override
    public Optional<Curso> findById(Long id) {
        return cursoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        cursoRepository.deleteById(id);
    }
}
