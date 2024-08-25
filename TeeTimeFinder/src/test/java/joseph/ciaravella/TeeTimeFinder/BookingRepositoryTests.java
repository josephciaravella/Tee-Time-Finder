package joseph.ciaravella.TeeTimeFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
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
import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;
import joseph.ciaravella.TeeTimeFinder.model.keys.BookingId;

@SpringBootTest
public class BookingRepositoryTests {
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private TeeTimeAvailabilityRepository teeTimeAvailabilityRepository;

    @Autowired
    private CustomerAccountRepository customerAccountRepository;

    @Autowired
    private CourseAdminAccountRepository courseAdminAccountRepository;

    @BeforeEach
    @AfterEach
    public void deleteAll() {
        bookingRepository.deleteAll();
        teeTimeAvailabilityRepository.deleteAll();
        customerAccountRepository.deleteAll();
        courseAdminAccountRepository.deleteAll();
    }

    @Test
    public void testPersistandLoadBooking() {
        CourseAdminAccount testCourseAdmin = new CourseAdminAccount("jer@prism.com", "hihihi");
        courseAdminAccountRepository.save(testCourseAdmin);

        Date testDate = Date.valueOf("2024-08-22");
        Time testTime = Time.valueOf("8:30:00");
        String testClubName = "Club de golf Terrebonne";
        String testCourseName = "Course 2";
        Integer testNumOfGolfers = 2;
        TeeTimeAvailability testTeeTimeAvailability = new TeeTimeAvailability(testClubName, testCourseName, testDate, testTime, testNumOfGolfers, testCourseAdmin);
        teeTimeAvailabilityRepository.save(testTeeTimeAvailability);

        CustomerAccount testCustomer = new CustomerAccount("arex@liu.com", "herro i hungly");
        customerAccountRepository.save(testCustomer);

        Date testDate2 = Date.valueOf(LocalDate.now());
        Booking testBooking = new Booking(testDate2, testCustomer, testTeeTimeAvailability);
        bookingRepository.save(testBooking);
        BookingId testBookingID = testBooking.getId();

        Optional<Booking> readBooking = bookingRepository.findById(testBookingID);
        assertNotNull(testBooking = (Booking) readBooking.orElse(null));
        assertEquals(testCustomer.getId(), testBooking.getCustomerAccount().getId());
        assertEquals(testTeeTimeAvailability.getId(), testBooking.getTeeTimeAvailability().getId());
        assertEquals(testDate2, testBooking.getBookingDate());
    }
}
