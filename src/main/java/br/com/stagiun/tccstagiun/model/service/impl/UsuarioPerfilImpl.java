package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.model.domain.UsuarioPerfil;
import br.com.stagiun.tccstagiun.model.repository.UsuarioPerfilRepository;
import br.com.stagiun.tccstagiun.model.service.UsuarioPerfilService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioPerfilImpl implements UsuarioPerfilService {

    @Autowired
    private UsuarioPerfilRepository usuarioperfilRepository;

    @Override
    public UsuarioPerfil salvar(UsuarioPerfil usuarioperfil) {
        return usuarioperfilRepository.save(usuarioperfil);
    }

    @Override
    public UsuarioPerfil editar(Long id, UsuarioPerfil usuarioperfil) {
       // usuarioperfil.setId(id);
        return usuarioperfilRepository.save(usuarioperfil);
    }

    @Override
    public List<UsuarioPerfil> list() {
        return usuarioperfilRepository.findAll();
    }

    @Override
    public Optional<UsuarioPerfil> findById(Long id) {
        return usuarioperfilRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        usuarioperfilRepository.deleteById(id);
    }

}
