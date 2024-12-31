package joseph.ciaravella.TeeTimeFinder.dto.UserAccount.CourseAdminAccount;

public class CourseAdminAccountDTO {
        
        private String email;
        private String associatedClub;
        
        public CourseAdminAccountDTO() {}
        
        public CourseAdminAccountDTO(String email, String associatedClub) {
            this.email = email;
            this.associatedClub = associatedClub;
        }
        
        public String getEmail() { return this.email; }
        public String getAssociatedClub() { return this.associatedClub; }

        public void setEmail(String email) { this.email = email; }
        public void setAssociatedClub(String associatedClub) { this.associatedClub = associatedClub; }
}
