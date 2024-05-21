package EJB;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.security.enterprise.identitystore.Pbkdf2PasswordHash;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class PasswordUtil {

    private Pbkdf2PasswordHash passwordHash;

    @PostConstruct
    public void init() {
        Map<String, String> parameters = new HashMap<>();
        parameters.put("Pbkdf2PasswordHash.Algorithm", "PBKDF2WithHmacSHA256");
        parameters.put("Pbkdf2PasswordHash.Iterations", "2048");
        parameters.put("Pbkdf2PasswordHash.SaltSizeBytes", "32");
        parameters.put("Pbkdf2PasswordHash.KeySizeBytes", "32");
        passwordHash.initialize(parameters);
    }

    public String hashPassword(String plainTextPassword) {
        return passwordHash.generate(plainTextPassword.toCharArray());
    }

    public boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return passwordHash.verify(plainTextPassword.toCharArray(), hashedPassword);
    }
}