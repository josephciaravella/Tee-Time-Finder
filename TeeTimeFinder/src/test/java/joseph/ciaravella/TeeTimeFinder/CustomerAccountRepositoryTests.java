package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import joseph.ciaravella.TeeTimeFinder.dao.CustomerAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;

@SpringBootTest
public class CustomerAccountRepositoryTests {
    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        customerAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCustomerAccount() {
        String testEmail = "joebama@gmail.com";
        String testPassword = "obama";

        CustomerAccount testCustomer = new CustomerAccount(testEmail, testPassword);

        customerAccountRepository.save(testCustomer);

        Optional<CustomerAccount> readCustomer = customerAccountRepository.findByEmail(testEmail);

        assertNotNull(testCustomer = readCustomer.orElse(null));
        assertEquals(testEmail, testCustomer.getEmail());
        assertEquals(testPassword, testCustomer.getPassword());
    }
}
