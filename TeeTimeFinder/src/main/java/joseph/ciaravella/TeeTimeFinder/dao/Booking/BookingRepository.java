package joseph.ciaravella.TeeTimeFinder.dao.Booking;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;
import joseph.ciaravella.TeeTimeFinder.model.keys.BookingId;

public interface BookingRepository extends CrudRepository<Booking, BookingId>, BookingRepositoryCustom {
    Optional<List<Booking>> findByCustomerAccount(CustomerAccount aCustomerAccount);
    Optional<List<Booking>> findByTeeTimeAvailability(TeeTimeAvailability aTeeTimeAvailability);
    Optional<List<Booking>> findByBookingDate(Date aBookingDate);
    Optional<List<Booking>> findByNumOfGolfers(Integer aNumOfGolfers);
}
