package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.Perfil;
import br.com.stagiun.tccstagiun.model.repository.PerfilRepository;
import br.com.stagiun.tccstagiun.model.service.PerfilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PerfilServiceImpl implements PerfilService {

    @Autowired
    private PerfilRepository perfilRepository;

    @Override
    public Perfil salvar(Perfil perfil) {
        return perfilRepository.save(perfil);
    }

    @Override
    public Perfil editar(Long id, Perfil perfil) {
        perfil.setId(id);
        return perfilRepository.save(perfil);
    }

    @Override
    public List<Perfil> list() {
        return perfilRepository.findAll();
    }

    @Override
    public Optional<Perfil> findById(Long id) {
        return perfilRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        perfilRepository.deleteById(id);
    }
}
