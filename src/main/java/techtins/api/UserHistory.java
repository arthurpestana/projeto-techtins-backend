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
    public String adminName;
    public String adminEmail;
    public String createdUserName;
    public String createdUserEmail;
    public LocalDate createdDate;
    public String actionType;
}
