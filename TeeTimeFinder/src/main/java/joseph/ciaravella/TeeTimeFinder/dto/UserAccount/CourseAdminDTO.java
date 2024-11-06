package joseph.ciaravella.TeeTimeFinder.dto.UserAccount;

import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

public class CourseAdminDTO {

    private String email;
    private Integer id;

    public CourseAdminDTO() {}

    public CourseAdminDTO(String email, Integer id) {
        this.email = email;
        this.id = id;
    }

    public CourseAdminDTO(UserAccount userAccount) {
        this(userAccount.getEmail(), userAccount.getId());
    }

    public String getEmail() { return this.email; }
    public Integer getId() { return this.id; }
    
            
}
