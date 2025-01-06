package joseph.ciaravella.TeeTimeFinder.dto.Booking;

import java.sql.Timestamp;
import java.time.ZoneId;

import joseph.ciaravella.TeeTimeFinder.model.Booking;

public class BookingDTOAdministrator {
    
    private Timestamp timestamp;
    private Integer numOfGolfers;
    private String customerAccountEmail;
    private Integer teeTimeAvailabilityId;

    public BookingDTOAdministrator() {}
    
    public BookingDTOAdministrator(Timestamp timestamp, Integer numOfGolfers, String customerAccountEmail, Integer teeTimeAvailabilityId) {
        this.timestamp = timestamp;
        this.numOfGolfers = numOfGolfers;
        this.customerAccountEmail = customerAccountEmail;
        this.teeTimeAvailabilityId = teeTimeAvailabilityId;
    }

    public BookingDTOAdministrator(Booking booking) {
        this.timestamp = Timestamp.valueOf(booking.getBookingDate().toLocalDate().atStartOfDay(ZoneId.of("US/Eastern")).toLocalDateTime());
        this.numOfGolfers = booking.getNumOfGolfers();
        this.customerAccountEmail = booking.getCustomerAccount().getEmail();
        this.teeTimeAvailabilityId = booking.getId().getTeeTimeAvailabilityId();
    }

    public Timestamp getTimestamp() { return this.timestamp; }
    public Integer getNumOfGolfers() { return this.numOfGolfers; }
    public String getCustomerAccountEmail() { return this.customerAccountEmail; }
    public Integer getTeeTimeAvailabilityId() { return this.teeTimeAvailabilityId; }
}
