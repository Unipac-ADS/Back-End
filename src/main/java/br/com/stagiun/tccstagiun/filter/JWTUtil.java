package br.com.stagiun.tccstagiun.filter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.util.Date;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class JWTUtil {

    public static final long EXPIRATION_TIME = 864_000_000; // 10 days
    //3_600_000 1 hora
    //public static final long EXPIRATION_TIME = 60_000;
    public static final String SECRET = "E7D86FC2-9474-477D-A69C-A3F470509DCD";
    public static final String TOKEN_PREFIX = "Bearer";
    public static final String HEADER_STRING = "Authorization";

    public static String createToken(String email) {
        String jwtToken = Jwts.builder().setSubject(email)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
        return TOKEN_PREFIX + " " + jwtToken;
    }
}
