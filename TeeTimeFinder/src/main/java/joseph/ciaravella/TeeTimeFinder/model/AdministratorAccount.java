package joseph.ciaravella.TeeTimeFinder.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMINISTRATOR")
public class AdministratorAccount extends UserAccount{

    public AdministratorAccount() {
        super();
    }

    public AdministratorAccount(String aEmail, String aPassword) {
        super(aEmail, aPassword);
    }
    
}
