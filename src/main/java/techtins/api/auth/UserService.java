package techtins.api.auth;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import techtins.api.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.Optional;

@ApplicationScoped
public class UserService {

    @PersistenceContext
    EntityManager entityManager;

    // Metodo para validar o usuário
    public User validateUser(String email, String senha) {
        // Buscando o usuário pelo email
        User user = findByEmail(email);

        // Verificando se o usuário existe e se a senha está correta
        if (user != null && BCrypt.checkpw(senha, user.senha)) {
            return user; // Retorna o usuário se as credenciais forem válidas
        }

        return null; // Retorna null se as credenciais forem inválidas
    }

    //Metodo para buscar um usuário pelo e-mail
    public User findByEmail(String email) {
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email)
                    .getSingleResult();
        } catch (Exception e) {
            return null; // Retorna null se o usuário não for encontrado
        }
    }
}

