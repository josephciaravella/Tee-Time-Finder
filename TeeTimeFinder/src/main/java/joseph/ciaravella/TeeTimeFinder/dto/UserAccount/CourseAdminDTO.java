package joseph.ciaravella.TeeTimeFinder.dto.UserAccount;

import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

public class CourseAdminDTO {

    private String email;
    private Integer id;
    private String associatedClub;

    public CourseAdminDTO() {}

    public CourseAdminDTO(String email, Integer id, String associatedClub) {
        this.email = email;
        this.id = id;
        this.associatedClub = associatedClub;
    }

    public CourseAdminDTO(UserAccount userAccount) {
        this(userAccount.getEmail(), userAccount.getId(), ((CourseAdminAccount) userAccount).getAssociatedClub());
    }

    public String getEmail() { return this.email; }
    public Integer getId() { return this.id; }
    public String getAssociatedClub() { return this.associatedClub; }
    
            
}
