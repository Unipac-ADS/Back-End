package br.com.stagiun.tccstagiun.model.service.impl;

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
    public Bairro salvar(Bairro bairro) {
        return bairroRepository.save(bairro);
    }

    @Override
    public Bairro editar(Long id, Bairro bairro) {
        bairro.setId(id);
        return bairroRepository.save(bairro);
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
