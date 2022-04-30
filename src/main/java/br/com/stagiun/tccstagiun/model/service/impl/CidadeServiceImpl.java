package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Cidade;
import br.com.stagiun.tccstagiun.model.repository.CidadeRepository;
import br.com.stagiun.tccstagiun.model.service.CidadeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CidadeServiceImpl implements CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Override
    public Cidade salvar(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    @Override
    public Cidade editar(Long id, Cidade cidade) {
        cidade.setId(id);
        return cidadeRepository.save(cidade);
    }

    @Override
    public List<Cidade> list() {
        return cidadeRepository.findAll();
    }

    @Override
    public Optional<Cidade> findById(Long id) {
        return cidadeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        cidadeRepository.deleteById(id);
    }
}
