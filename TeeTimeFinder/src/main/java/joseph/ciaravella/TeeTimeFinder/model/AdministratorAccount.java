package joseph.ciaravella.TeeTimeFinder.model;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
public class AdministratorAccount extends UserAccount{

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority("ADMIN"));
    }
    public AdministratorAccount() {
        super();
    }

    public AdministratorAccount(String aEmail, String aPassword) {
        super(aEmail, aPassword);
    }
    
}
