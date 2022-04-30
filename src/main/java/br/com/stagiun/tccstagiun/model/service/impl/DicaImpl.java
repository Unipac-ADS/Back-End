package br.com.stagiun.tccstagiun.model.service.impl;

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
    public Dica salvar(Dica dica) {
        return dicaRepository.save(dica);
    }

    @Override
    public Dica editar(Long id, Dica dica) {
        dica.setId(id);
        return dicaRepository.save(dica);
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
    public void delete(Long id) {
        dicaRepository.deleteById(id);
    }
}
