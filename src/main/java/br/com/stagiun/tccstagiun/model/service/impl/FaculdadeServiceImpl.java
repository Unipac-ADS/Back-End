package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Faculdade;
import br.com.stagiun.tccstagiun.model.repository.FaculdadeRepository;
import br.com.stagiun.tccstagiun.model.service.FaculdadeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FaculdadeServiceImpl implements FaculdadeService {

    @Autowired
    private FaculdadeRepository faculdadeRepository;

    @Override
    public Faculdade salvar(Faculdade faculdade) throws ResourceFoundException {
        Optional<Faculdade> existeFaculdade = findById(faculdade.getId());

        if (existeFaculdade.isPresent()) {
            throw new ResourceFoundException("Faculdade já encontrada!");
        }

        return faculdadeRepository.save(faculdade);
    }

    @Override
    public Faculdade editar(Long id, Faculdade faculdade) throws ResourceFoundException {
        Optional<Faculdade> existeFaculdade = findById(id);

        if (!existeFaculdade.isPresent()) {
            throw new ResourceFoundException("Faculdade não encontrado!");
        }

        Faculdade updateFaculdade = existeFaculdade.get();
        updateFaculdade.update(id, faculdade);
        return faculdadeRepository.save(updateFaculdade);
    }

    @Override
    public List<Faculdade> list() {
        return faculdadeRepository.findAll();
    }

    @Override
    public Optional<Faculdade> findById(Long id) {
        return faculdadeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        faculdadeRepository.deleteById(id);
    }
}
