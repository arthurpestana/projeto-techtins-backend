package techtins.api.auth;

public class UserLoginRequest {
    public String email;
    public String password;

    public UserLoginRequest() {}

    public UserLoginRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
