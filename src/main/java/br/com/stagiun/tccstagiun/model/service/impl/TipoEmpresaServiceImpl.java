package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.TipoEmpresa;
import br.com.stagiun.tccstagiun.model.repository.TipoEmpresaRepository;
import br.com.stagiun.tccstagiun.model.service.TipoEmpresaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TipoEmpresaServiceImpl implements TipoEmpresaService {

    @Autowired
    private TipoEmpresaRepository tipoEmpresaRepository;

    @Override
    public TipoEmpresa salvar(TipoEmpresa tipoEmpresa) throws ResourceFoundException {
        Optional<TipoEmpresa> existeTipoEmpresa = findByDescricao(tipoEmpresa.getDescricao());

        if (existeTipoEmpresa.isPresent()) {
            throw new ResourceFoundException("Tipo Empresa já encontrada!");
        }

        return tipoEmpresaRepository.save(tipoEmpresa);
    }

    @Override
    public TipoEmpresa editar(Long id, TipoEmpresa tipoEmpresa) throws ResourceFoundException{
        Optional<TipoEmpresa> existeTipoEmpresa = findById(id);

        if (!existeTipoEmpresa.isPresent()) {
            throw new ResourceFoundException("Tipo Empresa não encontrado!");
        }

        TipoEmpresa updateTipoEmpresa = existeTipoEmpresa.get();
        updateTipoEmpresa.update(id, tipoEmpresa);
        return tipoEmpresaRepository.save(updateTipoEmpresa);
    }

    @Override
    public List<TipoEmpresa> list() {
        return tipoEmpresaRepository.findAll();
    }

    @Override
    public Optional<TipoEmpresa> findById(Long id) {
        return tipoEmpresaRepository.findById(id);
    }

    @Override
    public Optional<TipoEmpresa> findByDescricao(String descricao) {
        return tipoEmpresaRepository.findByDescricao(descricao);
    }

    @Override
    public void delete(Long id) {
        tipoEmpresaRepository.deleteById(id);
    }
}
