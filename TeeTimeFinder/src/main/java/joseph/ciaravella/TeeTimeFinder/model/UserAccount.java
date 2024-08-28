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
  private String token;
  @Id
  @GeneratedValue
  private Integer id;

  public UserAccount() {}

  public UserAccount(String aEmail, String aPassword)
  {
    email = aEmail;
    password = aPassword;
  }


  public void setEmail(String aEmail) { this.email = aEmail; }

  public void setPassword(String aPassword) {this.password = aPassword; }

  public void setId(Integer aId) { this.id = aId; }

  public void setToken(String aToken) { this.token = aToken; }


  public String getEmail() { return this.email; }

  public String getPassword() { return this.password; }

  public Integer getId() { return this.id; }

  public String getToken() { return this.token; }


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