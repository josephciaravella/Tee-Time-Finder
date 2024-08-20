package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("COURSE ADMIN")
public class CourseAdminAccount extends UserAccount
{

  public CourseAdminAccount(String aEmail, String aPassword, Integer aId)
  {
    super(aEmail, aPassword, aId);
  }

  public void delete()
  {
    super.delete();
  }

}