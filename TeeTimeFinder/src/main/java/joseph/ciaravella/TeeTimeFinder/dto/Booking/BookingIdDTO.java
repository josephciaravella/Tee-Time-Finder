package joseph.ciaravella.TeeTimeFinder.dto.Booking;

import joseph.ciaravella.TeeTimeFinder.model.Booking;

public class BookingIdDTO {
    private Integer customerAccountId;
    private Integer teeTimeAvailabilityId;

    public BookingIdDTO() {}
    public BookingIdDTO(Integer customerAccountId, Integer teeTimeAvailabilityId) {
        this.customerAccountId = customerAccountId;
        this.teeTimeAvailabilityId = teeTimeAvailabilityId;
    }
    public BookingIdDTO(Booking booking) {
        this.customerAccountId = booking.getId().getCustomerAccountId();
        this.teeTimeAvailabilityId = booking.getId().getTeeTimeAvailabilityId();
    }

    public Integer getCustomerAccountId() { return this.customerAccountId; }
    public Integer getTeeTimeAvailabilityId() { return this.teeTimeAvailabilityId; }
}
