package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.repository.UsuarioRepository;
import br.com.stagiun.tccstagiun.model.service.UsuarioService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public Usuario salvar(Usuario usuario) throws ResourceFoundException {
        Optional<Usuario> existeUsuario = findById(usuario.getId());

        if (existeUsuario.isPresent()) {
            throw new ResourceFoundException("Usuário já encontrado");
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario editar(Long id, Usuario usuario) throws ResourceFoundException{
        Optional<Usuario> existeUsuario = findById(id);

        if (!existeUsuario.isPresent()) {
            throw new ResourceFoundException("Usuário não encontrado!");
        }

        Usuario updateUsuario = existeUsuario.get();
        updateUsuario.update(id, usuario);
        return usuarioRepository.save(updateUsuario);
    }

    @Override
    public List<Usuario> list() {
        return usuarioRepository.findAll();
    }

    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

}
