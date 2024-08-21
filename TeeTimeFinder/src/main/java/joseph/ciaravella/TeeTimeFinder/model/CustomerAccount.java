package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerAccount extends UserAccount
{

  public CustomerAccount() { super(); }

  public CustomerAccount(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
  }

  public void delete()
  {
    super.delete();
  }

}