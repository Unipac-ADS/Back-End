package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Endereco;
import br.com.stagiun.tccstagiun.model.repository.EnderecoRepository;
import br.com.stagiun.tccstagiun.model.service.EnderecoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EnderecoServiceImpl implements EnderecoService {

    @Autowired
    private EnderecoRepository enderecoRepository;

    @Override
    public Endereco salvar(Endereco endereco) {
        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco editar(Long id, Endereco endereco) {
        endereco.setId(id);
        return enderecoRepository.save(endereco);
    }

    @Override
    public List<Endereco> list() {
        return enderecoRepository.findAll();
    }

    @Override
    public Optional<Endereco> findById(Long id) {
        return enderecoRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        enderecoRepository.deleteById(id);
    }
}
