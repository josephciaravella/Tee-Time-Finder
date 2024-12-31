package joseph.ciaravella.TeeTimeFinder.dto.TeeTimeAvailability;

import java.sql.Timestamp;

import org.springframework.lang.Nullable;

import com.fasterxml.jackson.annotation.JsonFormat;

public class TeeTimeAvailabilityCO {
    
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private String timestamp;
    private Integer numOfGolfers;
    @Nullable
    private String courseName;

    public TeeTimeAvailabilityCO() {}
    public TeeTimeAvailabilityCO(String timestamp, Integer numOfGolfers, String courseName) {
        this.timestamp = timestamp;
        this.numOfGolfers = numOfGolfers;
        this.courseName = courseName;
    }

    public Timestamp getTimestamp() { return Timestamp.valueOf(this.timestamp.replace('T', ' ').substring(0, 19)); }
    public Integer getNumOfGolfers() { return this.numOfGolfers; }
    public String getCourseName() { return this.courseName; }
    
}
