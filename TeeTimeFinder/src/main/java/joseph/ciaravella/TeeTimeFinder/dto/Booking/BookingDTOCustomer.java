package joseph.ciaravella.TeeTimeFinder.dto.Booking;

import java.sql.Timestamp;
import java.time.ZoneId;

import joseph.ciaravella.TeeTimeFinder.model.Booking;

public class BookingDTOCustomer {
    private Timestamp timestamp;
    private Integer numOfGolfers;
    private Integer teeTimeAvailabilityId;

    public BookingDTOCustomer() {};

    public BookingDTOCustomer(Timestamp timestamp, Integer numOfGolfers, Integer teeTimeAvailabilityId) {
        this.timestamp = timestamp;
        this.numOfGolfers = numOfGolfers;
        this.teeTimeAvailabilityId = teeTimeAvailabilityId;
    }

    public BookingDTOCustomer(Booking booking) {
        this.timestamp = Timestamp.valueOf(booking.getBookingDate().toLocalDate().atStartOfDay(ZoneId.of("US/Eastern")).toLocalDateTime());
        this.numOfGolfers = booking.getNumOfGolfers();
        this.teeTimeAvailabilityId = booking.getId().getTeeTimeAvailabilityId();
    }

    public Timestamp getTimestamp() { return this.timestamp; }
    public Integer getNumOfGolfers() { return this.numOfGolfers; }
    public Integer getTeeTimeAvailabilityId() { return this.teeTimeAvailabilityId; }

}
