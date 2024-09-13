package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.util.Optional;
import java.time.LocalDate;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import joseph.ciaravella.TeeTimeFinder.dao.BookingRepository;
import joseph.ciaravella.TeeTimeFinder.dao.CourseAdminAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.CustomerAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailabilityRepository;
import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
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

        CourseAdminAccount testCourseAdmin = new CourseAdminAccount("joseph@bouba.com", "bouber", "Parcours du Cerf");
        courseAdminAccountRepository.save(testCourseAdmin);
        Date date = Date.valueOf("2024-08-18");
        Time time = Time.valueOf("10:00:00");
        String clubName = testCourseAdmin.getAssociatedClub();
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
        CourseAdminAccount testCourseAdmin = new CourseAdminAccount("joseph@bouba.com", "bouber", "Parcours du cerf");
        courseAdminAccountRepository.save(testCourseAdmin);

        Date date = Date.valueOf("2024-08-18");
        Time time = Time.valueOf("10:00:00");
        String clubName = testCourseAdmin.getAssociatedClub();
        String courseName = "Course 1";
        Integer numOfGolfers = 4;

        TeeTimeAvailability testTeeTimeAvailability = new TeeTimeAvailability(clubName, courseName, date, time, numOfGolfers, testCourseAdmin);

        teeTimeAvailabilityRepository.save(testTeeTimeAvailability);

        Integer generatedID = testTeeTimeAvailability.getId();

        teeTimeAvailabilityRepository.deleteById(generatedID);

        Optional<TeeTimeAvailability> deletedTeeTimeAvailability = teeTimeAvailabilityRepository.findById(testTeeTimeAvailability.getId());

        assertFalse(deletedTeeTimeAvailability.isPresent(), TeeTimeAvailability.class.getSimpleName() + "was not deleted successfully");
    }

    @Test
    public void testDeleteTeeTimeAvailabilityWithBooking() {
        CourseAdminAccount testCourseAdmin = new CourseAdminAccount("joseph@bouba.com", "bouber", "Parcours du Cerf");
        courseAdminAccountRepository.save(testCourseAdmin);
        Date date = Date.valueOf("2024-08-18");
        Time time = Time.valueOf("10:00:00");
        String clubName = testCourseAdmin.getAssociatedClub();
        String courseName = "Course 1";
        Integer numOfGolfers1 = 4;

        TeeTimeAvailability testTeeTimeAvailability = new TeeTimeAvailability(clubName, courseName, date, time, numOfGolfers1, testCourseAdmin);

        teeTimeAvailabilityRepository.save(testTeeTimeAvailability);

        CustomerAccount testCustomer = new CustomerAccount("joseph@gmail.com", "obama");
        customerAccountRepository.save(testCustomer);

        Integer numOfGolfers2 = 2;

        Booking testBooking = new Booking(Date.valueOf(LocalDate.now()), testCustomer, testTeeTimeAvailability, numOfGolfers2);
        bookingRepository.save(testBooking);

        Integer generatedTeeTimeAvailabilityID = testTeeTimeAvailability.getId();

        assertThrows(DataIntegrityViolationException.class, () -> teeTimeAvailabilityRepository.deleteById(generatedTeeTimeAvailabilityID));

        Optional<TeeTimeAvailability> notDeletedAvailability = teeTimeAvailabilityRepository.findById(generatedTeeTimeAvailabilityID);
        assertTrue(notDeletedAvailability.isPresent(), TeeTimeAvailability.class.getSimpleName() + "was deleted when it should not have been");
    }
}
