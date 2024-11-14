package joseph.ciaravella.TeeTimeFinder.dto.UserAccount;

import org.springframework.lang.Nullable;

public class UserAccountCO {
    @Nullable
    public String type;
    private String email;
    private String password;

    public UserAccountCO() {}

    public UserAccountCO(String type, String email, String password) {
        this.type = type;
        this.email = email;
        this.password = password;
    }

    public String getType() { return this.type; }
    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
}
