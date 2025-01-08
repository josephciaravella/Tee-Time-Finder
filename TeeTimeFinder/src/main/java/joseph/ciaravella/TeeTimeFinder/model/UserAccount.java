package joseph.ciaravella.TeeTimeFinder.model;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.*;

@Entity
@Table(name = "userAccounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "USER_TYPE")
public abstract class UserAccount implements UserDetails{

  //UserAccount Attributes
  private String email;
  private String password;
  private String token;
  @Id
  @GeneratedValue
  private Integer id;



  

  // UserDetails implementation
  @Override
  public String getUsername() {
      return email;
  }

  @Override
  public boolean isAccountNonExpired() {
      return true;
  }

  @Override
  public boolean isAccountNonLocked() {
      return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
      return true;
  }

  @Override
  public boolean isEnabled() {
      return true;
  }

  // Each subclass will implement getAuthorities()
  @Override
  public abstract Collection<? extends GrantedAuthority> getAuthorities();





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