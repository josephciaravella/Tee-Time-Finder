package joseph.ciaravella.TeeTimeFinder.dao.Booking;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;

public interface BookingRepositoryCustom {
    Optional<List<Booking>> findByFiltersAdmin(
        @Param("dateLow") Date dateLow,
        @Param("dateHigh") Date dateHigh,
        @Param("customer") CustomerAccount customer
    );

    Optional<List<Booking>> findByFiltersCustomer(
        @Param("dateLow") Date dateLow,
        @Param("dateHigh") Date dateHigh,
        CustomerAccount customer
    );
}
