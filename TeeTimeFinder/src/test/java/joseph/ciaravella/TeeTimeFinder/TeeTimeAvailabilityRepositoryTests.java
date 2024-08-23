package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import joseph.ciaravella.TeeTimeFinder.dao.BookingRepository;
import joseph.ciaravella.TeeTimeFinder.dao.CourseAdminAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.CustomerAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailabilityRepository;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;

@SpringBootTest
public class TeeTimeAvailabilityRepositoryTests {
    
    @Autowired
    private TeeTimeAvailabilityRepository teeTimeAvailabilityRepository;

    @Autowired
    private CourseAdminAccountRepository courseAdminAccountRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;


    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        bookingRepository.deleteAll();
        teeTimeAvailabilityRepository.deleteAll();
        customerAccountRepository.deleteAll();
        courseAdminAccountRepository.deleteAll();
    }

    @Test
    public void testPersistandLoadTeeTimeAvailability() {

        CourseAdminAccount testCourseAdmin = new CourseAdminAccount("joseph@bouba.com", "bouber");
        Date date = Date.valueOf("2024-08-18");
        Time time = Time.valueOf("10:00:00");
        String clubName = "Parcours du Cerf";
        String courseName = "Course 1";
        Integer numOfGolfers = 4;

        TeeTimeAvailability testTeeTimeAvailability = new TeeTimeAvailability(clubName, courseName, date, time, numOfGolfers, testCourseAdmin);

        teeTimeAvailabilityRepository.save(testTeeTimeAvailability);

        Integer generatedID = testTeeTimeAvailability.getId();

        Optional<TeeTimeAvailability> readTeeTimeAvailability = teeTimeAvailabilityRepository.findById(generatedID);

        assertNotNull(testTeeTimeAvailability = (TeeTimeAvailability) readTeeTimeAvailability.orElse(null));
        assertEquals(testCourseAdmin.getId(), testTeeTimeAvailability.getCourseAdminAccount().getId());
        assertEquals(date, testTeeTimeAvailability.getDate());
        assertEquals(time, testTeeTimeAvailability.getTime());
        assertEquals(clubName, testTeeTimeAvailability.getClubName());
        assertEquals(courseName, testTeeTimeAvailability.getCourseName());
        assertEquals(numOfGolfers, testTeeTimeAvailability.getNumOfGolfers());
    }

    @Test
    public void testDeleteTeeTimeAvailabilityWithoutBooking() {
        
    }
}
