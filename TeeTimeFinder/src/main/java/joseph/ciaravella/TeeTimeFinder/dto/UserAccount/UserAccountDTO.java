package joseph.ciaravella.TeeTimeFinder.dto.UserAccount;

import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

public class UserAccountDTO {
    
    public String type;
    private String email;
    private String token;

    public UserAccountDTO() {}

    public UserAccountDTO(String type, String email, String token) {
        this.type = type;
        this.email = email;
        this.token = token;
    }

    public UserAccountDTO(UserAccount userAccount) {
        this (userAccount.getUserType(), userAccount.getEmail(), userAccount.getToken());
    }

    public String getToken(){ return this.token;}

    public String getType() { return this.type; }
    public String getEmail() { return this.email; }

}
