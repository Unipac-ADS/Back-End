package br.com.stagiun.tccstagiun.filter;

public interface PasswordCryptoService {
    String encrypt(String password);
    boolean matches(String rawPassword, String encodedPassword);
}
