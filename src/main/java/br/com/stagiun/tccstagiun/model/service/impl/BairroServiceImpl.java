package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Bairro;
import br.com.stagiun.tccstagiun.model.repository.BairroRepository;
import br.com.stagiun.tccstagiun.model.service.BairroService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BairroServiceImpl implements BairroService {

    @Autowired
    private BairroRepository bairroRepository;

    @Override
    public Bairro salvar(Bairro bairro) throws ResourceFoundException {
        Optional<Bairro> existeBairro = findById(bairro.getId());

        if (existeBairro.isPresent()) {
            throw new ResourceFoundException("Bairro já encontrado!");
        }

        return bairroRepository.save(bairro);
    }

    @Override
    public Bairro editar(Long id, Bairro bairro) throws ResourceFoundException {
        Optional<Bairro> existeBairro = findById(id);

        if (!existeBairro.isPresent()) {
            throw new ResourceFoundException("Bairro não encontrado!");
        }

        Bairro updateBairro = existeBairro.get();
        updateBairro.update(id, bairro);
        return bairroRepository.save(updateBairro);
    }

    @Override
    public List<Bairro> list() {
        return bairroRepository.findAll();
    }

    @Override
    public Optional<Bairro> findById(Long id) {
        return bairroRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        bairroRepository.deleteById(id);
    }
}
