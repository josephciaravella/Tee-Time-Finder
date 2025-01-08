package joseph.ciaravella.TeeTimeFinder.controller;

import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import joseph.ciaravella.TeeTimeFinder.dto.Booking.BookingCO;
import joseph.ciaravella.TeeTimeFinder.dto.Booking.BookingDTOAdministrator;
import joseph.ciaravella.TeeTimeFinder.dto.Booking.BookingDTOCustomer;
import joseph.ciaravella.TeeTimeFinder.dto.Booking.BookingIdDTO;
import joseph.ciaravella.TeeTimeFinder.model.Booking;
import joseph.ciaravella.TeeTimeFinder.model.keys.BookingId;
import joseph.ciaravella.TeeTimeFinder.service.BookingService;

@CrossOrigin(origins = "*")
@RestController
public class BookingRestController {
    
    @Autowired
    BookingService bookingService;

    @PostMapping("/bookings/newBooking")
    public ResponseEntity<?> createNewBooking(@RequestHeader String userToken, @RequestBody BookingCO bookingCO) {
        try {
            bookingService.createBooking(bookingCO.getNumOfGolfers(), userToken, bookingCO.getTeeTimeAvailabilityID());
            return ResponseEntity.status(HttpStatus.CREATED).body("Booking successfully created!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/bookings/getBooking")
    public ResponseEntity<?> getBooking(@RequestHeader String userToken, @RequestBody BookingIdDTO bookingID) {
        try {
            Booking booking = bookingService.getBooking(userToken, new BookingId(bookingID.getCustomerAccountId(), bookingID.getTeeTimeAvailabilityId()));
            return ResponseEntity.ok().body(new BookingDTOCustomer(booking));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("bookings/getAllBookingsCustomer")
    public ResponseEntity<?> getAllBookingsCustomer(@RequestHeader String userToken, 
        @RequestParam(required = false) Date dateLow,
        @RequestParam(required = false) Date dateHigh) {
        try {
            List<Booking> bookings = bookingService.getAllBookingsCustomer(dateLow, dateHigh, userToken);
            List<BookingDTOCustomer> bookingDTOs = bookings.stream().map(b -> new BookingDTOCustomer(b)).collect(Collectors.toList());
            return ResponseEntity.ok().body(bookingDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/admin/bookings/getAllBookingsAdmin")
    public ResponseEntity<?> getAllBookingsAdmin(@RequestHeader String userToken, 
        @RequestParam(required = false) String email,
        @RequestParam(required = false) Date dateLow,
        @RequestParam(required = false) Date dateHigh) {
        try {
            List<Booking> bookings = bookingService.getAllBookingsAdministrator(dateLow, dateHigh, userToken, email);
            List<BookingDTOAdministrator> bookingDTOs = bookings.stream().map(b -> new BookingDTOAdministrator(b)).collect(Collectors.toList());
            return ResponseEntity.ok().body(bookingDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @DeleteMapping("/bookings/deleteBooking")
    public ResponseEntity<?> deleteBooking(@RequestHeader String userToken, @RequestBody BookingIdDTO bookingID) {
        try {
            bookingService.deleteBooking(userToken, new BookingId(bookingID.getCustomerAccountId(), bookingID.getTeeTimeAvailabilityId()));
            return ResponseEntity.ok().body("Booking Successfully deleted!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
