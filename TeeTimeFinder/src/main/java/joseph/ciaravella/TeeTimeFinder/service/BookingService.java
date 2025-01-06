package joseph.ciaravella.TeeTimeFinder.service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.Booking.BookingRepository;
import joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailability.TeeTimeAvailabilityRepository;
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

        bookingRepository.save(newBooking);
    }

    // might not need this
    @Transactional
    public Booking getBooking(String userToken, BookingId id) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);

        if (user == null) {
            throw new IllegalArgumentException("No user found with this token!");
        }
        
        if (!user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Only customers can see their registrations!");
        }
        
        if (!user.getId().equals(id.getCustomerAccountId())) {
            throw new IllegalArgumentException("Only the customer who made this booking can see it!");
        }

        Booking foundBooking = bookingRepository.findById(id).orElse(null);

        if (foundBooking == null) {
            throw new IllegalArgumentException("Booking not found!");
        }

        return foundBooking;
    }

    // i dont think i need these

    // // FOR CUSTOMER
    // @Transactional
    // public List<Booking> getAllBookingsCustomer(String userToken) {
    //     UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);

    //     if (!user.getUserType().equals("CUSTOMER")) {
    //         throw new IllegalArgumentException("Only customers can see all of their registrations!");
    //     }

    //     List<Booking> foundBookings = bookingRepository.findByCustomerAccount((CustomerAccount) user).orElse(null);

    //     if (foundBookings == null) {
    //         throw new IllegalArgumentException("No bookings found!");
    //     }

    //     return foundBookings;
    // }

    // // FOR ADMINISTRATOR
    // @Transactional
    // public List<Booking> getAllBookingsAdministrator(String userToken, String email) {
    //     UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);

    //     if (!user.getUserType().equals("ADMINISTRATOR")) {
    //         throw new IllegalArgumentException("Only the administrator can see all customer bookings!");
    //     }

    //     UserAccount customer = userAccountRepository.findUserByEmail(email).orElse(null);

    //     if (customer != null && !customer.getUserType().equals("CUSTOMER")) {
    //         throw new IllegalArgumentException("This type of user is not a customer!");
    //     }

    //     List<Booking> foundBookings = bookingRepository.findByCustomerAccount((CustomerAccount) customer).orElse(null);

    //     if (foundBookings == null) {
    //         throw new IllegalArgumentException("This customer has not made any bookings!");
    //     }

    //     return foundBookings;
    // }
    @Transactional
    public List<Booking> getAllBookingsCustomer(Date lowDate, Date highDate, String userToken) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);
        
        if (user.getUserType().equals("CUSTOMER")) {
            List<Booking> foundBookings = bookingRepository.findByFiltersCustomer(lowDate, highDate, (CustomerAccount) user).orElse(null);
            return foundBookings;
        }

        if (user.getUserType().equals("ADMINISTRATOR")) {
            throw new IllegalArgumentException("No customer specified!");
        }

        else {
            throw new IllegalArgumentException("CourseAdmins cannot view customers bookings!");
        }
    }

    @Transactional
    public List<Booking> getAllBookingsAdministrator(Date lowDate, Date highDate, String userToken, String email) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);
        UserAccount customer = userAccountRepository.findUserByEmail(email).orElse(null);

        if (customer == null && email == null) {
            throw new IllegalArgumentException("No user email was provided!");
        }

        if (customer == null && !email.isEmpty()) {
            throw new IllegalArgumentException("No user was found with this email!");
        }

        if (user.getUserType().equals("ADMINISTRATOR")) {
            List<Booking> foundBookings = bookingRepository.findByFiltersAdmin(lowDate, highDate, (CustomerAccount) customer).orElse(null);
            return foundBookings;
        } 

        else if (user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Customers cannot view other customers' bookings!");
        }

        else {
            throw new IllegalArgumentException("CourseAdmins cannot view customer bookings!");
        }
    }

    @Transactional
    public void deleteBooking(String userToken, BookingId id) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, userToken);

        if (!user.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Only customers can delete their registration!");
        }
        
        if (!user.getId().equals(id.getCustomerAccountId())) {
            throw new IllegalArgumentException("Only the customer who made this booking can delete it!");
        }

        Booking foundBooking = bookingRepository.findById(id).orElse(null);

        if (foundBooking == null) {
            throw new IllegalArgumentException("No booking found!");
        }

        TeeTimeAvailability foundTeeTimeAvailability = teeTimeAvailabilityRepository.findById(id.getTeeTimeAvailabilityId()).orElse(null);

        if (foundTeeTimeAvailability == null) {
            throw new IllegalArgumentException("Tee time not found!");
        }


        teeTimeAvailabilityService.updateTeeTimeAvailability(foundBooking.getNumOfGolfers() + foundTeeTimeAvailability.getNumOfGolfers(), id.getTeeTimeAvailabilityId());
        bookingRepository.delete(foundBooking);
    }




}
