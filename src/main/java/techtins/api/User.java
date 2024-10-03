package techtins.api;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="user")
public class User extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;
    public String nome;
    public String sobrenome;
    public String email;
    public String senha; // Campo de senha
    public String funcao = "Cliente"; // Pode ser "Admin", "Cliente" ou "Funcionário"
    public LocalDate dataCadastro;
    public String status = "Ativo"; // "Ativo" ou "Inativo"
    public String fotoUrl; // URL da foto
    public String endereco; // Endereço completo
    public String genero = "Outro"; // Gênero ("Masculino", "Feminino", "Outro")

    // Construtor vazio
    public User() {}

    // Construtor com todos os campos
    public User(String nome, String sobrenome, String email, String senha, String funcao, String status, String fotoUrl, String endereco, String genero) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        this.senha = senha;
        this.funcao = funcao;
        this.dataCadastro = LocalDate.now();
        this.status = status;
        this.fotoUrl = fotoUrl;
        this.endereco = endereco;
        this.genero = genero;
    }
}
