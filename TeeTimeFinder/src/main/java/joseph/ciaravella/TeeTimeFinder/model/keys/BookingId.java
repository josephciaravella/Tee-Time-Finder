package joseph.ciaravella.TeeTimeFinder.model.keys;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BookingId implements Serializable {
    @Column(name = "customer_account_id")
    private Integer customerAccountId;
    @Column(name = "tee_time_availability_id")
    private Integer teeTimeAvailabilityId;

    public BookingId () {}
    public BookingId (Integer customerAccountId, Integer teeTimeAvailabilityId) {
        this.customerAccountId = customerAccountId;
        this.teeTimeAvailabilityId = teeTimeAvailabilityId;
    }

    public boolean equals (BookingId that) {
        return (this.teeTimeAvailabilityId.equals(that.teeTimeAvailabilityId) && this.customerAccountId.equals(that.customerAccountId));
    }

    public Integer getCustomerAccountId() { return customerAccountId; }
    public Integer getteeTimeAvailabilityId() { return teeTimeAvailabilityId; }
}
