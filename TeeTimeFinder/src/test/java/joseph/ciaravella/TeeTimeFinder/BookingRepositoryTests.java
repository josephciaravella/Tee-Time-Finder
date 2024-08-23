package joseph.ciaravella.TeeTimeFinder;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import joseph.ciaravella.TeeTimeFinder.dao.BookingRepository;

@SpringBootTest
public class BookingRepositoryTests {
    
    @Autowired
    private BookingRepository bookingRepository;

    @BeforeEach
    @AfterEach
    public void deleteAll() {
        bookingRepository.deleteAll();
    }

    @Test
    public void testPersistandLoadBooking() {
        
    }
}
