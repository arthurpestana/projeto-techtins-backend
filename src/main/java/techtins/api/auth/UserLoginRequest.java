package techtins.api.auth;

public class UserLoginRequest {
    public String email;
    public String senha;

    public UserLoginRequest() {}

    public UserLoginRequest(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }
}
