package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import joseph.ciaravella.TeeTimeFinder.dao.AdministratorAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.AdministratorAccount;

@SpringBootTest
public class AdministratorAccountRepositoryTests {

    @Autowired
    private AdministratorAccountRepository administratorAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        administratorAccountRepository.deleteAll(); 
    }

    @Test
    public void testPersistAndLoadAdministratorAccount() {
        String testEmail = "joe@bama.com";
        String testPassword = "password";
        AdministratorAccount testAdmin = new AdministratorAccount(testEmail, testPassword);
        administratorAccountRepository.save(testAdmin);

        Optional<AdministratorAccount> readAdmin = administratorAccountRepository.findByEmail(testEmail);
        assertNotNull(testAdmin = readAdmin.orElse(null));
        assertEquals(testEmail, testAdmin.getEmail());
        assertEquals(testPassword, testAdmin.getPassword());
    }
    
}
