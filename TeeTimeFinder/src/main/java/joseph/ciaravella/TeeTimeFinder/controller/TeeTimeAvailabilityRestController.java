package joseph.ciaravella.TeeTimeFinder.controller;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import joseph.ciaravella.TeeTimeFinder.dto.TeeTimeAvailability.TeeTimeAvailabilityCO;
import joseph.ciaravella.TeeTimeFinder.dto.TeeTimeAvailability.TeeTimeAvailabilityDTO;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;
import joseph.ciaravella.TeeTimeFinder.service.TeeTimeAvailabilityService;

@CrossOrigin(origins = "*")
@RestController
public class TeeTimeAvailabilityRestController {
    
    @Autowired
    private TeeTimeAvailabilityService teeTimeAvailabilityService;

    
    
    // create tee time availability
    @PostMapping("/teeTime/createTeeTimeAvailability")
    public ResponseEntity<?> createTeeTimeAvailability(@RequestHeader String userToken, @RequestBody TeeTimeAvailabilityCO teeTimeAvailabilityCO) {
        try {
            if (teeTimeAvailabilityCO.getTimestamp() == null) {
                throw new IllegalArgumentException("Timestamp cannot be null");
            }
            LocalDate date = teeTimeAvailabilityCO.getTimestamp().toLocalDateTime().toLocalDate();
            LocalTime time = teeTimeAvailabilityCO.getTimestamp().toLocalDateTime().toLocalTime();
            teeTimeAvailabilityService.createTeeTimeAvailability(teeTimeAvailabilityCO.getCourseName(), teeTimeAvailabilityCO.getNumOfGolfers(), userToken, date, time);
            return ResponseEntity.status(HttpStatus.CREATED).body("Tee time availability created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // update tee time availability - num of golfers changed
    @PutMapping("/teeTime/updateTeeTimeAvailability")
    public ResponseEntity<?> updateTeeTimeAvailability(@RequestHeader String userToken, @RequestParam Integer newNumOfGolfers, @RequestHeader Integer teeTimeID) {
        try {
            teeTimeAvailabilityService.updateTeeTimeAvailability(userToken, newNumOfGolfers, teeTimeID, true);
            return ResponseEntity.status(HttpStatus.OK).body("Tee time availability updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get tee time availability by id
    @GetMapping("/teeTimes/getTeeTimeAvailability")
    public ResponseEntity<?> getTeeTimeAvailability(@RequestHeader Integer teeTimeID) {
        try {
            TeeTimeAvailability foundTeeTime = teeTimeAvailabilityService.getTeeTimeAvailabilityById(teeTimeID);
            if (foundTeeTime == null) {
                throw new IllegalArgumentException("Tee time availability not found!");
            }
            return ResponseEntity.status(HttpStatus.OK).body(new TeeTimeAvailabilityDTO(foundTeeTime));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get list of tee time availabilities
    @GetMapping("/public/teeTimes/getAllTeeTimeAvailabilities")
    public ResponseEntity<?> getTeeTimeAvailabilities(
        @RequestParam(required = false) Time lowTime,
        @RequestParam(required = false) Time highTime,
        @RequestParam(required = false) Date lowDate,
        @RequestParam(required = false) Date highDate,
        @RequestParam(required = false) String club,
        @RequestParam(required = false) String course,
        @RequestParam(required = false) Integer numOfGolfers) {
        
        try {
            List<TeeTimeAvailability> foundTeeTimes = teeTimeAvailabilityService.getAllTeeTimeAvailabilities(lowTime, highTime, lowDate, highDate, club, course, numOfGolfers);
            List<TeeTimeAvailabilityDTO> teeTimeAvailabilityDTOs = foundTeeTimes.stream().map(t -> new TeeTimeAvailabilityDTO(t)).collect(Collectors.toList());
            return ResponseEntity.status(HttpStatus.OK).body(teeTimeAvailabilityDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    // delete tee time availability
    @DeleteMapping("/teeTime/deleteTeeTimeAvailability")
    public ResponseEntity<?> deleteTeeTimeAvailability(@RequestHeader String userToken, @RequestHeader Integer teeTimeID) {
        try {
            teeTimeAvailabilityService.deleteTeeTimeAvailability(userToken, teeTimeID);
            return ResponseEntity.status(HttpStatus.OK).body("Tee time availability deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
