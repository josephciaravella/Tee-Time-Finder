package joseph.ciaravella.TeeTimeFinder.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.*;

@Entity
@DiscriminatorValue("CUSTOMER")
public class CustomerAccount extends UserAccount
{

  public CustomerAccount() { super(); }

  public Collection<? extends GrantedAuthority> getAuthorities() {
    return Collections.singletonList(new SimpleGrantedAuthority("USER"));
  }

  public CustomerAccount(String aEmail, String aPassword)
  {
    super(aEmail, aPassword);
  }

  public void delete()
  {
    super.delete();
  }

}