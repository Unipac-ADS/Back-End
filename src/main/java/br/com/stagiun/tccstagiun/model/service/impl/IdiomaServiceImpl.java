package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Idioma;
import br.com.stagiun.tccstagiun.model.repository.IdiomaRepository;
import br.com.stagiun.tccstagiun.model.service.IdiomaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IdiomaServiceImpl implements IdiomaService {

    @Autowired
    private IdiomaRepository idiomaRepository;

    @Override
    public Idioma salvar(Idioma idioma) throws ResourceFoundException {
        Optional<Idioma> existeIdioma = findByNome(idioma.getNome());

        if (existeIdioma.isPresent()) {
            throw new ResourceFoundException("Idioma já encontrado!");
        }

        return idiomaRepository.save(idioma);
    }

    @Override
    public Idioma editar(Long id, Idioma idioma) throws ResourceFoundException{
        Optional<Idioma> existeIdioma = findById(id);

        if (!existeIdioma.isPresent()) {
            throw new ResourceFoundException("Idioma não encontrado!");
        }

        Idioma updateIdioma = existeIdioma.get();
        updateIdioma.update(id, idioma);
        return idiomaRepository.save(updateIdioma);
    }

    @Override
    public List<Idioma> list() {
        return idiomaRepository.findAll();
    }

    @Override
    public Optional<Idioma> findById(Long id) {
        return idiomaRepository.findById(id);
    }

    @Override
    public Optional<Idioma> findByNome(String nome) {
        return idiomaRepository.findByNome(nome);
    }

    @Override
    public void delete(Long id) {
        idiomaRepository.deleteById(id);
    }
}
