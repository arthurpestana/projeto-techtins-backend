package techtins.api.security;

import io.smallrye.jwt.build.Jwt;
import java.time.Duration;
import java.util.Set;

public class JwtUtils {

    public static String generateToken(String username, String email, Set<String> roles) {
        return Jwt.issuer("https://localhost:8080")  // O emissor do token
                .subject(email)
                .claim("email", email)
                .claim("username", username)
                .groups(roles)  // Roles do usuário
                .expiresIn(Duration.ofHours(2))  // Expiração do token
                .sign();  // Assina e gera o token
    }
}

