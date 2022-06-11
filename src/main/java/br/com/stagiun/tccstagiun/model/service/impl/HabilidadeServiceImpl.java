package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Habilidade;
import br.com.stagiun.tccstagiun.model.repository.HabilidadeRepository;
import br.com.stagiun.tccstagiun.model.service.HabilidadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HabilidadeServiceImpl implements HabilidadeService {

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @Override
    public Habilidade salvar(Habilidade habilidade) throws ResourceFoundException {
        Optional<Habilidade> existeHabilidade = findByNome(habilidade.getNome());

        if (existeHabilidade.isPresent()) {
            throw new ResourceFoundException("Habilidade já encontrada!");
        }

        return habilidadeRepository.save(habilidade);
    }

    @Override
    public Habilidade editar(Long id, Habilidade habilidade) throws ResourceFoundException{
        Optional<Habilidade> existeHabilidade = findById(id);

        if (!existeHabilidade.isPresent()) {
            throw new ResourceFoundException("Habilidade não encontrada!");
        }

        Habilidade updateHabilidade = existeHabilidade.get();
        updateHabilidade.update(id, habilidade);
        return habilidadeRepository.save(updateHabilidade);
    }

    @Override
    public List<Habilidade> list() {
        return habilidadeRepository.findAll();
    }

    @Override
    public Optional<Habilidade> findById(Long id) {
        return habilidadeRepository.findById(id);
    }


    @Override
    public Optional<Habilidade> findByNome(String nome) {
        return habilidadeRepository.findByNome(nome);
    }

    @Override
    public void delete(Long id) {
        habilidadeRepository.deleteById(id);
    }
}
