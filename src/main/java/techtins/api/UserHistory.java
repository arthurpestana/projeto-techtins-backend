package techtins.api;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="user_history")
public class UserHistory extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String adminName; // Nome do admin que criou o usuário
    public String adminEmail; // Email do admin que criou o usuário
    public String createdUserName; // Nome do usuário criado
    public String createdUserEmail;
    public LocalDate createdDate; // Data de criação do usuário
    public String actionType;
}
