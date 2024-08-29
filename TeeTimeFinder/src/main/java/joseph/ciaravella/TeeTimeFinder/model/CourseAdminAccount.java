package joseph.ciaravella.TeeTimeFinder.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("COURSE ADMIN")
public class CourseAdminAccount extends UserAccount
{

  private String associatedClub;

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("USER"));
  }

  public CourseAdminAccount() { super(); }

  public CourseAdminAccount(String aEmail, String aPassword, String aAssociatedClub)
  {
    super(aEmail, aPassword);
    this.associatedClub = aAssociatedClub;
  }

  public boolean setAssociatedClub(String aAssociatedClub) {
    boolean wasSet = false;
    this.associatedClub = aAssociatedClub;
    wasSet = true;
    return wasSet;
  }

  public String getAssociatedClub() {
    return this.associatedClub;
  }

  public void delete()
  {
    super.delete();
  }

}