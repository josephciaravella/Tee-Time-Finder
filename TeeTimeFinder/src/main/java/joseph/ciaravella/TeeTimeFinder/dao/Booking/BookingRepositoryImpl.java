package joseph.ciaravella.TeeTimeFinder.dao.Booking;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;

public class BookingRepositoryImpl implements BookingRepositoryCustom {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<List<Booking>> findByFiltersAdmin(Date dateLow, Date dateHigh, CustomerAccount customer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
        List<Predicate> predicateList = new ArrayList<>();

        Root<Booking> booking = criteriaQuery.from(Booking.class);

        if (customer != null) {
            predicateList.add(criteriaBuilder.equal(booking.get("customerAccount").get("id"), customer.getId()));
        }

        if (dateLow != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(booking.get("teeTimeAvailability").get("date"), dateLow));
        }

        if (dateHigh != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(booking.get("teeTimeAvailability").get("date"), dateHigh));
        }

        criteriaQuery.select(booking).where(predicateList.toArray(new Predicate[0]));

        List<Booking> result = entityManager.createQuery(criteriaQuery).getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result);
    }

    @Override
    public Optional<List<Booking>> findByFiltersCustomer(Date dateLow, Date dateHigh, CustomerAccount customer) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Booking> criteriaQuery = criteriaBuilder.createQuery(Booking.class);
        List<Predicate> predicateList = new ArrayList<>();

        Root<Booking> booking = criteriaQuery.from(Booking.class);
        
        predicateList.add(criteriaBuilder.equal(booking.get("customerAccount").get("id"), customer.getId()));

        if (dateLow != null) {
            predicateList.add(criteriaBuilder.greaterThanOrEqualTo(booking.get("teeTimeAvailability").get("date"), dateLow));
        }

        if (dateHigh != null) {
            predicateList.add(criteriaBuilder.lessThanOrEqualTo(booking.get("teeTimeAvailability").get("date"), dateHigh));
        }

        criteriaQuery.select(booking).where(predicateList.toArray(new Predicate[0]));

        List<Booking> result = entityManager.createQuery(criteriaQuery).getResultList();
        return Optional.ofNullable(result.isEmpty() ? null : result);
    }
        
}
