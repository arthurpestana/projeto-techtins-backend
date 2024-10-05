package techtins.api.auth;

import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import techtins.api.User;
import techtins.api.security.JwtUtils;
import jakarta.annotation.security.RolesAllowed;
import techtins.api.security.JwtRequired;

import java.util.Set;

@Path("/auth")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    UserService userService; // Um serviço que valida o usuário

    @POST
    @Path("/login")
    @Transactional
    public Response login(UserLoginRequest request) {
        // Verificar se o usuário e a senha são válidos
        System.out.println("Login request: " + request.password + " " + request.email);
        User user = userService.validateUser(request.email, request.password);

        if (user == null || !user.funcao.equals("Admin")) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        }
        System.out.println("Usuário Verificado");

        // Gerar o token JWT para o admin
        String token = JwtUtils.generateToken(user.nome+" "+user.sobrenome,user.email, Set.of("Admin"));

        // Retornar o token para o frontend
        return Response.ok(new AuthResponse(token)).build();
    }
}