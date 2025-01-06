package joseph.ciaravella.TeeTimeFinder.dto.Booking;

public class BookingCO {
    private Integer numOfGolfers;
    private Integer teeTimeAvailabilityID;

    public BookingCO() {}
    public BookingCO(Integer numOfGolfers, Integer teeTimeAvailabilityID) {
        this.numOfGolfers = numOfGolfers;
        this.teeTimeAvailabilityID = teeTimeAvailabilityID;
    }

    public Integer getNumOfGolfers() { return this.numOfGolfers; }
    public Integer getTeeTimeAvailabilityID() { return this.teeTimeAvailabilityID; }
}
