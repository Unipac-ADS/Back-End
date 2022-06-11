package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Dica;
import br.com.stagiun.tccstagiun.model.repository.DicaRepository;
import br.com.stagiun.tccstagiun.model.service.DicaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class DicaImpl implements DicaService {

    @Autowired
    private DicaRepository dicaRepository;

    @Override
    public Dica salvar(Dica dica) throws ResourceFoundException {
        Optional<Dica> existeDica = findByTitulo(dica.getTitulo());

        if (existeDica.isPresent()) {
            throw new ResourceFoundException("Dica já encontrada!");
        }

        return dicaRepository.save(dica);
    }

    @Override
    public Dica editar(Long id, Dica dica) throws ResourceFoundException {
        Optional<Dica> existeDica = findById(id);

        if (!existeDica.isPresent()) {
            throw new ResourceFoundException("Dica não encontrada!");
        }

        Dica updateDica = existeDica.get();
        updateDica.update(id, dica);
        return dicaRepository.save(updateDica);
    }

    @Override
    public List<Dica> list() {
        return dicaRepository.findAll();
    }

    @Override
    public Optional<Dica> findById(Long id) {
        return dicaRepository.findById(id);
    }

    @Override
    public Optional<Dica> findByTitulo(String titulo) {
        return dicaRepository.findByTitulo(titulo);
    }

    @Override
    public void delete(Long id) {
        dicaRepository.deleteById(id);
    }
}
