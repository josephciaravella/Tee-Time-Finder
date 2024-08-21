package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.*;

@Entity
@Table(name = "userAccounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public class UserAccount
{

  //UserAccount Attributes
  private String email;
  private String password;
  @Id
  @GeneratedValue
  private Integer id;

  public UserAccount() {}

  public UserAccount(String aEmail, String aPassword)
  {
    email = aEmail;
    password = aPassword;
  }


  public boolean setEmail(String aEmail)
  {
    boolean wasSet = false;
    email = aEmail;
    wasSet = true;
    return wasSet;
  }

  public boolean setPassword(String aPassword)
  {
    boolean wasSet = false;
    password = aPassword;
    wasSet = true;
    return wasSet;
  }

  public boolean setId(Integer aId)
  {
    boolean wasSet = false;
    id = aId;
    wasSet = true;
    return wasSet;
  }

  public String getEmail() { return email; }

  public String getPassword() { return password; }

  public Integer getId() { return id; }

  public void delete(){}

  @Transient
  public String getUserType() { return this.getClass().getAnnotation(DiscriminatorValue.class).value(); }

  public String toString()
  {
    return super.toString() + "["+
            "email" + ":" + getEmail()+ "," +
            "password" + ":" + getPassword()+ "," +
            "id" + ":" + getId()+ "]";
  }
}