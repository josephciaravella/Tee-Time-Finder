package joseph.ciaravella.TeeTimeFinder.dto.Authentication;

public class AuthenticationCO {
    public String email;
    public String password;

    public AuthenticationCO() {
    }

    public AuthenticationCO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() { return email; }
    public String getPassword() { return password; }
}
