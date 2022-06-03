package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Estado;
import br.com.stagiun.tccstagiun.model.repository.EstadoRepository;
import br.com.stagiun.tccstagiun.model.service.EstadoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EstadoServiceImpl implements EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    @Override
    public Estado salvar(Estado estado) throws ResourceFoundException {
        Optional<Estado> existeEstado = findByDescricao(estado.getDescricao());

        if (existeEstado.isPresent()) {
            throw new ResourceFoundException("Estado já encontrado!");
        }

        return estadoRepository.save(estado);
    }

    @Override
    public Estado editar(Long id, Estado estado) throws ResourceFoundException{
        Optional<Estado> existeEstado = findById(id);

        if (!existeEstado.isPresent()) {
            throw new ResourceFoundException("Estado não encontrado!");
        }

        Estado updateEstado = existeEstado.get();
        updateEstado.update(id, estado);
        return estadoRepository.save(updateEstado);
    }

    @Override
    public List<Estado> list() {
        return estadoRepository.findAll();
    }

    @Override
    public Optional<Estado> findById(Long id) {
        return estadoRepository.findById(id);
    }

    @Override
    public Optional<Estado> findByDescricao(String descricao) {
        return estadoRepository.findByDescricao(descricao);
    }

    @Override
    public void delete(Long id) {
        estadoRepository.deleteById(id);
    }
}
