package br.com.stagiun.tccstagiun.filter;

import br.com.stagiun.tccstagiun.model.domain.Usuario;
import br.com.stagiun.tccstagiun.model.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email).map(user -> {
            return criaUserParaAutenticacao(user);
        }).orElse(null);
    }

    private UserDetails criaUserParaAutenticacao(Usuario usuario) {
        if (!usuario.getAuthorities().isEmpty()) {
            List<GrantedAuthority> authorities = usuario.getAuthorities().stream().collect(Collectors.toList());
            log.info("Authorities {}", authorities);
            return new org.springframework.security.core.userdetails.User(usuario.getEmail(), usuario.getPassword(), authorities);
        }

        return null;
    }
}
