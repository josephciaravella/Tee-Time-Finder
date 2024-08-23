package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import joseph.ciaravella.TeeTimeFinder.dao.CourseAdminAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;

@SpringBootTest
public class CourseAdminAccountRepositoryTests {

    @Autowired
    private CourseAdminAccountRepository courseAdminAccountRepository;

    @BeforeEach
    @AfterEach
    public void deleteAll() {
        courseAdminAccountRepository.deleteAll();
    }

    @Test
    public void testPersistAndLoadCourseAdminAccount() {

        String testEmail = "waymond@parcours.com";
        String testPassword = "cerf";

        CourseAdminAccount testCourseAdminAccount = new CourseAdminAccount(testEmail, testPassword);

        courseAdminAccountRepository.save(testCourseAdminAccount);

        Optional<CourseAdminAccount> readCourseAdmin = courseAdminAccountRepository.findByEmail(testEmail);

        assertNotNull(testCourseAdminAccount = (CourseAdminAccount) readCourseAdmin.orElse(null));
        assertEquals(testEmail, testCourseAdminAccount.getEmail());
        assertEquals(testPassword, testCourseAdminAccount.getPassword());
    }
    
}
