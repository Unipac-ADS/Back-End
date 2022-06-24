package br.com.stagiun.tccstagiun.model.service.impl;

import br.com.stagiun.tccstagiun.exceptions.ResourceFoundException;
import br.com.stagiun.tccstagiun.exceptions.ResourceNotFoundException;
import br.com.stagiun.tccstagiun.filter.JWTUtil;
import br.com.stagiun.tccstagiun.filter.PasswordCryptoService;
import br.com.stagiun.tccstagiun.filter.TokenResponse;
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

    @Autowired
    private PasswordCryptoService passwordCryptoService;

    @Override
    public Usuario salvar(Usuario usuario) throws ResourceFoundException {
        try {
            Optional<Usuario> existeUsuario = findByNome(usuario.getNome());

            if (existeUsuario.isPresent()) {
                throw new ResourceFoundException("Usuário já encontrado");
            }

            usuario.setSenha(passwordCryptoService.encrypt(usuario.getSenha()));
            log.info("Usuario para cadastrar {}", usuario);
            return usuarioRepository.save(usuario);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.info("Erro ao cadastrar {}", ex.getMessage());
        }
        return null;
    }

    @Override
    public Usuario editar(Long id, Usuario usuario) throws ResourceFoundException {
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
    public Optional<Usuario> findByNome(String nome) {
        return usuarioRepository.findByNome(nome);
    }

    @Override
    public TokenResponse getLoginAndReturnToken(Usuario usuario) throws ResourceNotFoundException {

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());

        if (usuarioOptional.isPresent()) {
            return TokenResponse.builder()
                    .userId(usuarioOptional.get().getId())
                    .token(JWTUtil.createToken(usuarioOptional.get().getEmail()))
                    .perfils(usuarioOptional.get().getPerfis()).build();
        }

        return null;
    }

    @Override
    public void delete(Long id) {
        usuarioRepository.deleteById(id);
    }

}
