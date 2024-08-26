package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("COURSE ADMIN")
public class CourseAdminAccount extends UserAccount
{

  private String associatedCourse;

  public CourseAdminAccount() { super(); }

  public CourseAdminAccount(String aEmail, String aPassword, String aAssociatedCourse)
  {
    super(aEmail, aPassword);
    this.associatedCourse = aAssociatedCourse;
  }

  public boolean setAssociatedCourse(String aAssociatedCourse) {
    boolean wasSet = false;
    this.associatedCourse = aAssociatedCourse;
    wasSet = true;
    return wasSet;
  }

  public String getAssociatedCourse() {
    return this.associatedCourse;
  }

  public void delete()
  {
    super.delete();
  }

}