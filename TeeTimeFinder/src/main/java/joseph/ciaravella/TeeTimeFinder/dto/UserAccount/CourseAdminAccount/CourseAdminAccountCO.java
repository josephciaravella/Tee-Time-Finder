package joseph.ciaravella.TeeTimeFinder.dto.UserAccount.CourseAdminAccount;

public class CourseAdminAccountCO {
    private String email;
    private String password;
    private String associatedClub;

    public CourseAdminAccountCO() {}

    public CourseAdminAccountCO(String email, String password, String associatedClub) {
        this.email = email;
        this.password = password;
        this.associatedClub = associatedClub;
    }

    public String getEmail() { return this.email; }
    public String getPassword() { return this.password; }
    public String getAssociatedClub() { return this.associatedClub; }
}
