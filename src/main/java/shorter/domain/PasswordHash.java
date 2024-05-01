package shorter.domain;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public record PasswordHash(String hash) {

    public static PasswordHash encode(String password) {
        var encoder = new BCryptPasswordEncoder();
        return new PasswordHash(encoder.encode(password));
    }

    @Override
    public String hash() {
        return "{bcrypt}" + hash;
    }
}