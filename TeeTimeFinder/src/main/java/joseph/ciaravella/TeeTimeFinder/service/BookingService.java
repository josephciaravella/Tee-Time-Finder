package joseph.ciaravella.TeeTimeFinder.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.BookingRepository;
import joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailabilityRepository;
import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.model.keys.BookingId;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;

@Service
public class BookingService {
    
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    TeeTimeAvailabilityRepository teeTimeAvailabilityRepository;

    @Autowired
    TeeTimeAvailabilityService teeTimeAvailabilityService;

    @Transactional
    public void createBooking(Integer numOfGolfers, String userToken, Integer teeTimeId) {
        // check that user is a customer
        UserAccount foundUser = Utilities.getUserWithToken(userAccountRepository, userToken);
        if (!foundUser.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Only customers can register for tee times!");
        }
        // check that a tee time exists with this id
        TeeTimeAvailability foundTeeTime = teeTimeAvailabilityRepository.findById(teeTimeId).orElse(null);
        if (foundTeeTime == null) {
            throw new IllegalArgumentException("No tee time was found with this ID!");
        }

        if (foundTeeTime.getNumOfGolfers() < numOfGolfers) {
            throw new IllegalArgumentException("Cannot have more golfers than spaces available!");
        }
        Integer remainingSpaces = foundTeeTime.getNumOfGolfers() - numOfGolfers;
        // create registration
        BookingId bookingId = new BookingId(foundUser.getId(), teeTimeId);
        Booking newBooking = new Booking();
        newBooking.setBookingDate(Date.valueOf(LocalDate.now()));
        newBooking.setCustomerAccount((CustomerAccount) foundUser);
        newBooking.setTeeTimeAvailability(foundTeeTime);
        newBooking.setNumOfGolfers(numOfGolfers);
        newBooking.setId(bookingId);
        teeTimeAvailabilityService.updateTeeTimeAvailability(remainingSpaces, teeTimeId);

    }

    // might not need this
    @Transactional
    public Booking getBooking(BookingId id) {
        Booking foundBooking = bookingRepository.findById(id).orElse(null);

        if (foundBooking == null) {
            throw new IllegalArgumentException("Booking not found!");
        }

        return foundBooking;
    }

    @Transactional
    public List<Booking> getAllBookingsPerCustomer(String userToken) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Only customers can see all of their registrations!");
        }

        List<Booking> foundBookings = bookingRepository.findByCustomerAccount((CustomerAccount) user).orElse(null);

        if (foundBookings == null) {
            throw new IllegalArgumentException("No bookings found!");
        }

        return foundBookings;
    }

    @Transactional
    public void deleteBooking(String userToken, BookingId id) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Only customers can delete their registration!");
        }

        Booking foundBooking = bookingRepository.findById(id).orElse(null);

        if (foundBooking == null) {
            throw new IllegalArgumentException("No booking found!");
        }

        TeeTimeAvailability foundTeeTimeAvailability = teeTimeAvailabilityRepository.findById(id.getteeTimeAvailabilityId()).orElse(null);

        if (foundTeeTimeAvailability == null) {
            throw new IllegalArgumentException("Tee time not found!");
        }


        bookingRepository.delete(foundBooking);
        teeTimeAvailabilityService.updateTeeTimeAvailability(foundBooking.getNumOfGolfers() + foundTeeTimeAvailability.getNumOfGolfers(), id.getteeTimeAvailabilityId());
    }




}
