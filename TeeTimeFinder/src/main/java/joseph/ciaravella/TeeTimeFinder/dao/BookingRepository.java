package joseph.ciaravella.TeeTimeFinder.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;
import joseph.ciaravella.TeeTimeFinder.model.keys.BookingId;

public interface BookingRepository extends CrudRepository<Booking, BookingId> {
    Optional<List<Booking>> findByCustomerAccount(CustomerAccount aCustomerAccount);
    Optional<List<Booking>> findByTeeTimeAvailability(TeeTimeAvailability aTeeTimeAvailability);
    Optional<List<Booking>> findByBookingDate(Date aBookingDate);

    @Query("SELECT b FROM Booking b WHERE " +
            "(:dateLow is null or b.bookingDate >= :dateLow) and " +
            "(:dateHigh is null or b.bookingDate <= :dateHigh)")

    Optional<List<Booking>> findByFilters(
        @Param("dateLow") Date dateLow,
        @Param("dateHigh") Date dateHigh
    );
}
