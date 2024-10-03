package techtins.api.security;

import org.eclipse.microprofile.jwt.JsonWebToken;

import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Set;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class JwtFilter implements ContainerRequestFilter {

    @Inject
    JsonWebToken jwt;

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // Verifica se o metodo ou classe possui a anotação JwtRequired
        Method method = (Method) requestContext.getProperty("jakarta.ws.rs.core.Method");
        if (method != null && method.isAnnotationPresent(JwtRequired.class)) {
            // Verifica se o JWT está presente
            if (jwt == null || jwt.getName() == null) {
                requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).entity("Token não encontrado ou inválido.").build());
                return;
            }

            // Verifica se o usuário é Admin
            Set<String> roles = jwt.getGroups();
            if (!roles.contains("Admin")) {
                requestContext.abortWith(Response.status(Response.Status.FORBIDDEN).entity("Permissão negada. Apenas Admins podem realizar esta ação.").build());
            }
        }
    }
}

