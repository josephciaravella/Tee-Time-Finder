package joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailability;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;

public interface TeeTimeAvailabilityRepository extends CrudRepository<TeeTimeAvailability, Integer>, TeeTimeAvailabilityRepositoryCustom {
    Optional<List<TeeTimeAvailability>> findByClubName(String clubName);
    Optional<List<TeeTimeAvailability>> findByCourseName(String courseName);
    Optional<List<TeeTimeAvailability>> findByTime(Time time);
    Optional<List<TeeTimeAvailability>> findByDate(Date date);
    Optional<List<TeeTimeAvailability>> findByNumOfGolfers(Integer numOfGolfers);
}
