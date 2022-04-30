package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Empresa;
import br.com.stagiun.tccstagiun.model.repository.EmpresaRepository;
import br.com.stagiun.tccstagiun.model.service.EmpresaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class EmpresaImpl implements EmpresaService {

    @Autowired
    private EmpresaRepository empresaRepository;

    @Override
    public Empresa salvar(Empresa empresa) {
        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa editar(Long id, Empresa empresa) {
        empresa.setId(id);
        return empresaRepository.save(empresa);
    }

    @Override
    public List<Empresa> list() {
        return empresaRepository.findAll();
    }

    @Override
    public Optional<Empresa> findById(Long id) {
        return empresaRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        empresaRepository.deleteById(id);
    }
}
