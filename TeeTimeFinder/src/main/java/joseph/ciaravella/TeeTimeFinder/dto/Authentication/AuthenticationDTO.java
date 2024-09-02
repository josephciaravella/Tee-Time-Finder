package joseph.ciaravella.TeeTimeFinder.dto.Authentication;

public class AuthenticationDTO {

    private String token;
    private String email;
    private String role;

    public AuthenticationDTO() {}
    public AuthenticationDTO(String email, String token, String role) {
        this.token = token;
        this.email = email;
        this.role = role;
    }

    public String getToken() { return this.token; }
    public String getRole() { return this.role; }
    public String getEmail() { return this.email; }

    public void setToken(String token) { this.token = token; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    
}
