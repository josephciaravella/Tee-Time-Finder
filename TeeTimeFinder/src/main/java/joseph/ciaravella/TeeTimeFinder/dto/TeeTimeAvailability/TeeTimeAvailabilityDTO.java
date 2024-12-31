package joseph.ciaravella.TeeTimeFinder.dto.TeeTimeAvailability;

import java.sql.Date;
import java.sql.Time;

import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;

public class TeeTimeAvailabilityDTO {

    private Integer id;
    private String clubName;
    private String courseName;
    private Integer numOfGolfers;
    private Date date;
    private Time time;

    public TeeTimeAvailabilityDTO() {}
    public TeeTimeAvailabilityDTO(TeeTimeAvailability teeTimeAvailability) {
        this.id = teeTimeAvailability.getId();
        this.clubName = teeTimeAvailability.getClubName();
        this.courseName = teeTimeAvailability.getCourseName();
        this.numOfGolfers = teeTimeAvailability.getNumOfGolfers();
        this.date = teeTimeAvailability.getDate();
        this.time = teeTimeAvailability.getTime();
    }

    public Integer getId() { return this.id; }
    public String getClubName() { return this.clubName; }
    public String getCourseName() { return this.courseName; }
    public Integer getNumOfGolfers() { return this.numOfGolfers; }
    public Date getDate() { return this.date; }
    public Time getTime() { return this.time; }
}
