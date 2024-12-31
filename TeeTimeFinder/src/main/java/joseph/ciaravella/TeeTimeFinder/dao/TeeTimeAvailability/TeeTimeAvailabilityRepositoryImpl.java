package joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailability;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;

public class TeeTimeAvailabilityRepositoryImpl implements TeeTimeAvailabilityRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<TeeTimeAvailability>> findByFilters(
        Integer numOfGolfers,
        Time timeLow,
        Time timeHigh,
        Date dateLow,
        Date dateHigh,
        String club,
        String course
    ) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<TeeTimeAvailability> criteriaQuery = criteriaBuilder.createQuery(TeeTimeAvailability.class);
        List<Predicate> predicateList = new ArrayList<>();
        
        // SELECT * from TeeTimeAvailability t
        Root<TeeTimeAvailability> teeTimeAvailability = criteriaQuery.from(TeeTimeAvailability.class);

        // WHERE t.numOfGolfers >= golfers
        if (numOfGolfers != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(teeTimeAvailability.get("numOfGolfers"), numOfGolfers));
        }

        // WHERE t.time >= timeLow
        if (timeLow != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(teeTimeAvailability.get("time"), timeLow));
        }

        // WHERE t.time <= timeHigh
        if (timeHigh != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(teeTimeAvailability.get("time"), timeHigh));
        }

        // WHERE t.date >= dateLow
        if (dateLow != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(teeTimeAvailability.get("date"), dateLow));
        }

        // WHERE t.date <= dateHigh
        if (dateHigh != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(teeTimeAvailability.get("date"), dateHigh));
        }

        // WHERE t.clubName == club 
        if (club != null && !club.isEmpty()) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.lower(teeTimeAvailability.get("clubName")), club.toLowerCase()));
        }

        // WHERE t.courseName == course
        if (course != null && !course.isEmpty()) {
            predicateList.add(criteriaBuilder.equal(criteriaBuilder.lower(teeTimeAvailability.get("courseName")), course.toLowerCase()));
        }

        Predicate finalPredicate = criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        criteriaQuery.where(finalPredicate);
        List<TeeTimeAvailability> result = entityManager.createQuery(criteriaQuery).getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result);
    }
}
