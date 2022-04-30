package br.com.stagiun.tccstagiun.model.service.impl;

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
    public TipoEmpresa salvar(TipoEmpresa tipoEmpresa) {
        return tipoEmpresaRepository.save(tipoEmpresa);
    }

    @Override
    public TipoEmpresa editar(Long id, TipoEmpresa tipoEmpresa) {
        tipoEmpresa.setId(id);
        return tipoEmpresaRepository.save(tipoEmpresa);
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
    public void delete(Long id) {
        tipoEmpresaRepository.deleteById(id);
    }
}
