package techtins.api;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import jakarta.inject.Inject;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.mindrot.jbcrypt.BCrypt;

import jakarta.annotation.security.RolesAllowed;
import techtins.api.security.JwtRequired;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    JsonWebToken jwt;

    // Exemplo de criação do admin temporário (este endpoint pode ser removido após o uso)
    @POST
    @Path("/create-admin")
    @Transactional
    public Response createAdmin() {
        // Verifica se o admin já existe
        User existingAdmin = User.find("email", "admin@example.com").firstResult();
        if (existingAdmin != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Admin já existe").build();
        }

        // Cria um novo usuário Admin
        User admin = new User();
        admin.nome = "Admin";
        admin.sobrenome = "User";
        admin.email = "admin@example.com";
        admin.senha = BCrypt.hashpw("adminPassword", BCrypt.gensalt()); // Senha criptografada
        admin.funcao = "Admin"; // Função de Admin
        admin.status = "Ativo";
        admin.dataCadastro = java.time.LocalDate.now();

        // Persistindo o Admin no banco de dados
        admin.persist();

        return Response.status(Response.Status.CREATED).entity(admin).build();
    }

    @GET
    @JwtRequired
    @RolesAllowed("Admin")
    public List<User> getAllUsers() {
        return User.listAll();
    }

    @GET
    @Path("/{id}")
    @JwtRequired
    @RolesAllowed("Admin")
    public User getUserById(@PathParam("id") Long id) {
        return User.findById(id);
    }

    @POST
    @Transactional
    @JwtRequired
    @RolesAllowed("Admin")
    public Response createUser(User user, @HeaderParam("Authorization") String authToken) {
        User existingUser = User.find("email", user.email).firstResult();
        if (existingUser != null) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Usuário já cadastrado.").build();
        }

        if (user == null || user.email == null || user.senha == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        // Salvando o novo usuário
        user.senha = BCrypt.hashpw(user.senha, BCrypt.gensalt());
        user.dataCadastro = java.time.LocalDate.now();
        user.persist();

        persistUserHistory(user, "Cadastro");

        return Response.status(Response.Status.CREATED).entity(user).build();
    }

    @PUT
    @Path("/{id}")
    @Transactional
    @JwtRequired
    @RolesAllowed("Admin")
    public Response updateUser(@PathParam("id") Long id, User updatedUser) {
        User user = User.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // Atualizando os campos
        user.nome = updatedUser.nome;
        user.sobrenome = updatedUser.sobrenome;
        user.email = updatedUser.email;
        if (updatedUser.senha != null && !updatedUser.senha.isEmpty()) {
            user.senha = BCrypt.hashpw(updatedUser.senha, BCrypt.gensalt());
        }
        user.funcao = updatedUser.funcao;
        user.status = updatedUser.status;
        user.endereco = updatedUser.endereco;
        user.fotoUrl = updatedUser.fotoUrl;
        user.genero = updatedUser.genero;
        user.persist();

        persistUserHistory(user, "Atualização");

        return Response.ok(user).build();
    }

    @DELETE
    @Path("/{id}")
    @Transactional
    @JwtRequired
    @RolesAllowed("Admin")
    public Response deleteUser(@PathParam("id") Long id) {
        User user = User.findById(id);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        persistUserHistory(user, "Deleção");
        user.delete();
        return Response.noContent().build();
    }

    @GET
    @Path("/history")
    @JwtRequired
    @RolesAllowed("Admin")
    public List<UserHistory> getUserHistory() {
        return UserHistory.listAll();
    }

    private void persistUserHistory(User user, String type) {
        UserHistory history = new UserHistory();
        history.adminName = jwt.getClaim("username");
        history.adminEmail = jwt.getClaim("email");
        history.createdUserName = user.nome + " " + user.sobrenome;
        history.createdUserEmail = user.email;
        history.createdDate = java.time.LocalDate.now();
        history.actionType = type;
        history.persist();
    }
}
