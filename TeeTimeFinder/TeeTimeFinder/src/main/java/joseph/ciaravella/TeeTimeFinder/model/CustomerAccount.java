package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerAccount extends UserAccount
{

  public CustomerAccount(String aEmail, String aPassword, Integer aId)
  {
    super(aEmail, aPassword, aId);
  }

  public void delete()
  {
    super.delete();
  }

}