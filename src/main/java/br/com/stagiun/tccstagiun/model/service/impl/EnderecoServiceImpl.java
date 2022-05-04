package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Empresa;
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
    public Endereco salvar(Endereco endereco) throws ResourceFoundException {
        Optional<Endereco> existeEndereco = findById(endereco.getId());

        if (existeEndereco.isPresent()) {
            throw new ResourceFoundException("Endereço já encontrado!");
        }

        return enderecoRepository.save(endereco);
    }

    @Override
    public Endereco editar(Long id, Endereco endereco) throws ResourceFoundException {
        Optional<Endereco> existeEndereco = findById(id);

        if (!existeEndereco.isPresent()) {
            throw new ResourceFoundException("Endereço não encontrado!");
        }

        Endereco updateEndereco = existeEndereco.get();
        updateEndereco.update(id, endereco);
        return enderecoRepository.save(updateEndereco);
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
