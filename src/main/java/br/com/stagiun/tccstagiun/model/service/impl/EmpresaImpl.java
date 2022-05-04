package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
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
    public Empresa salvar(Empresa empresa) throws ResourceFoundException {
        Optional<Empresa> existeEmpresa = findByCnpj(empresa.getCnpj());

        if (existeEmpresa.isPresent()) {
            throw new ResourceFoundException("Empresa já encontrada!");
        }

        return empresaRepository.save(empresa);
    }

    @Override
    public Empresa editar(Long id, Empresa empresa) throws ResourceFoundException {
        Optional<Empresa> existeEmpresa = findById(id);

        if (!existeEmpresa.isPresent()) {
            throw new ResourceFoundException("Empresa não encontrada!");
        }

        Empresa updateEmpresa = existeEmpresa.get();
        updateEmpresa.update(id, empresa);
        return empresaRepository.save(updateEmpresa);
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

    @Override
    public Optional<Empresa> findByCnpj(Integer cnpj) {
        return empresaRepository.findByCnpj(cnpj);
    }
}
