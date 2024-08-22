package joseph.ciaravella.TeeTimeFinder.dao;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;

public interface TeeTimeAvailabilityRepository extends CrudRepository<TeeTimeAvailability, Integer> {
    Optional<List<TeeTimeAvailability>> findByClubName(String clubName);
    Optional<List<TeeTimeAvailability>> findByCourseName(String courseName);
    Optional<List<TeeTimeAvailability>> findByTime(Time time);
    Optional<List<TeeTimeAvailability>> findByDate(Date date);

    @Query("SELECT t FROM TeeTimeAvailability WHERE " +
            "(:timeLow is null or t.time >= :timeLow) and " +
            "(:timeHigh is null or t.time <= :timeHigh) and " +
            "(:dateLow is null or t.date >= :dateLow) and " +
            "(:dateHigh is null or t.date <= :dateHigh)")

    Optional<List<TeeTimeAvailability>> findByFilters(
        @Param("timeLow") Time timeLow,
        @Param("timeHigh") Time timeHigh,
        @Param("dateLow") Date dateLow,
        @Param("dateHigh") Date dateHigh
    );
}
