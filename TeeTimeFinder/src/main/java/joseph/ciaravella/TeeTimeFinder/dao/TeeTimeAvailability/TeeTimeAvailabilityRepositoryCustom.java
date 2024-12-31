package joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailability;

import java.sql.Date;
import java.sql.Time;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;

public interface TeeTimeAvailabilityRepositoryCustom {
    Optional<List<TeeTimeAvailability>> findByFilters(
        @Param("golfers") Integer numOfGolfers,
        @Param("timeLow") Time timeLow,
        @Param("timeHigh") Time timeHigh,
        @Param("dateLow") Date dateLow,
        @Param("dateHigh") Date dateHigh,
        @Param("club") String club,
        @Param("course") String course
    );
}
