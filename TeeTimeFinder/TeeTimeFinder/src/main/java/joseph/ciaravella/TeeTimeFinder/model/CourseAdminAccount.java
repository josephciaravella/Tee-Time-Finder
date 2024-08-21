package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("COURSE ADMIN")
public class CourseAdminAccount extends UserAccount
{

  public CourseAdminAccount() { super(); }

  public CourseAdminAccount(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
  }

  public void delete()
  {
    super.delete();
  }

}