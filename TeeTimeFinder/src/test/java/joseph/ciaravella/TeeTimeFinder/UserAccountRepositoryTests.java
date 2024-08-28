package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

@SpringBootTest
public class UserAccountRepositoryTests {
    
    @Autowired
    private UserAccountRepository userAccountRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        userAccountRepository.deleteAll();
    }

    @Test
    public void testAndPersistUsers() {
        String testCustomerEmail = "joseph@gmail.com";
        String testCustomerPassword = "bouba";

        CustomerAccount testCustomer = new CustomerAccount(testCustomerEmail, testCustomerPassword);

        String testCourseAdminEmail = "parcours@cerf.com";
        String testCourseAdminPassword = "golf";
        String testAssociatedClub = "Parcours du cerf";

        CourseAdminAccount testCourseAdmin = new CourseAdminAccount(testCourseAdminEmail, testCourseAdminPassword, testAssociatedClub);

        // testCustomer.setToken(tokenP);

        userAccountRepository.save(testCustomer);
        userAccountRepository.save(testCourseAdmin);

        Optional<UserAccount> readCustomer = userAccountRepository.findUserByEmail(testCustomerEmail);
        Optional<UserAccount> readCourseAdmin = userAccountRepository.findUserByEmail(testCourseAdminEmail);

        assertNotNull(testCustomer = (CustomerAccount) readCustomer.orElse(null));
        assertEquals(testCustomerEmail, testCustomer.getEmail());
        assertEquals(testCustomerPassword, testCustomer.getPassword());

        assertNotNull(testCourseAdmin = (CourseAdminAccount) readCourseAdmin.orElse(null));
        assertEquals(testCourseAdminEmail, testCourseAdmin.getEmail());
        assertEquals(testCourseAdminPassword, testCourseAdmin.getPassword());
        assertEquals(testAssociatedClub, testCourseAdmin.getAssociatedClub());
    }
}
