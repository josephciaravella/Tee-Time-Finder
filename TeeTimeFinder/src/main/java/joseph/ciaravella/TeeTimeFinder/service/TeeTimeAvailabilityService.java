package joseph.ciaravella.TeeTimeFinder.service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.CourseAdminAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.TeeTimeAvailabilityRepository;
import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.TeeTimeAvailability;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;

@Service
public class TeeTimeAvailabilityService {

    @Autowired
    TeeTimeAvailabilityRepository teeTimeAvailabilityRepository;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    CourseAdminAccountRepository courseAdminAccountRepository;

    @Transactional
    public void createTeeTimeAvailabliity(String aCourseName, Integer aNumOfGolfers, String aUserToken, Date aDate, Time aTime) {
        UserAccount foundUser = userAccountRepository.findUserByToken(aUserToken).orElse(null);
        if (foundUser == null) {
            throw new IllegalArgumentException("User not found!");
        }

        if (!foundUser.getUserType().equals("COURSE ADMIN")) {
            throw new IllegalArgumentException("Only course admins can create tee time availabilities!");
        }

        CourseAdminAccount courseAdmin = (CourseAdminAccount) foundUser;

        Date today = Date.valueOf(LocalDate.now());
        Time currTime = Time.valueOf(LocalTime.now());

        if (aDate.before(today) || aTime.before(currTime)) {
            throw new IllegalArgumentException("This date and/or time has already passed!");
        }

        if (aCourseName.trim().isEmpty()) {
            throw new IllegalArgumentException("You must specify for which course this tee time is available");
        }

        if (aNumOfGolfers <= 0) {
            throw new IllegalArgumentException("The number of golfers cannot be less than or equal to 0!");
        }

        if (aNumOfGolfers > 4) {
            throw new IllegalArgumentException("The number of golfers cannot be greater than 4!");
        }
        TeeTimeAvailability teeTime = new TeeTimeAvailability();

        teeTime.setClubName(courseAdmin.getAssociatedClub());
        teeTime.setCourseAdminAccount(courseAdmin);
        teeTime.setCourseName(aCourseName);
        teeTime.setDate(aDate);
        teeTime.setTime(aTime);
        teeTime.setNumOfGolfers(aNumOfGolfers);

        teeTimeAvailabilityRepository.save(teeTime);
    }
    
    @Transactional
    // public void updateTeeTimeAvailability(Integer newNumOfGolfers, String aUserToken, Integer aId) {
    public void updateTeeTimeAvailability(Integer newNumOfGolfers, Integer aId) {
        // UserAccount foundUser = userAccountRepository.findUserByToken(aUserToken).orElse(null);
        // if (foundUser == null) {
        //     throw new IllegalArgumentException("User not found!");
        // }

        // if (!foundUser.getUserType().equals("COURSE ADMIN")) {
        //     throw new IllegalArgumentException("Only course admins can update tee time availabilities!");
        // }

        // CourseAdminAccount courseAdmin = (CourseAdminAccount) foundUser;

        TeeTimeAvailability teeTime = teeTimeAvailabilityRepository.findById(aId).orElse(null);

        // // do i need this??
        // if (!courseAdmin.getId().equals(teeTime.getCourseAdminAccount().getId())) {
        //     throw new IllegalArgumentException("Only the creator of this tee time availability can modify it");
        // }

        if (newNumOfGolfers < 0) {
            throw new IllegalArgumentException("The number of golfers cannot be less than 0!");
        }

        if (newNumOfGolfers >= teeTime.getNumOfGolfers()) {
            throw new IllegalArgumentException("The new number of golfers permitted must be less than the current number of golfers permitted!");
        }

        teeTime.setNumOfGolfers(newNumOfGolfers);

        teeTimeAvailabilityRepository.save(teeTime);
    }

    @Transactional
    public TeeTimeAvailability getTeeTimeAvailabilityById(Integer aId) {
        TeeTimeAvailability foundTeeTime = teeTimeAvailabilityRepository.findById(aId).orElse(null);

        if (foundTeeTime == null) {
            throw new IllegalArgumentException("Tee time availability not found!");
        }

        return foundTeeTime;
    }

    @Transactional
    public List<TeeTimeAvailability> getTeeTimeAvailabilitiesByClub(String aClubName) {
        List<TeeTimeAvailability> foundTeeTimes = teeTimeAvailabilityRepository.findByClubName(aClubName).orElse(null);

        if (foundTeeTimes == null) {
            throw new IllegalArgumentException("No tee times available for the specified club!");
        }

        return foundTeeTimes;
    }

    @Transactional
    public List<TeeTimeAvailability> getTeeTimeAvailabilitiesByTime(Time aTime) {
        List<TeeTimeAvailability> foundTeeTimes = teeTimeAvailabilityRepository.findByTime(aTime).orElse(null);

        if (foundTeeTimes == null) {
            throw new IllegalArgumentException("No tee times available for the specified time!");
        }

        return foundTeeTimes;
    }

    @Transactional
    public List<TeeTimeAvailability> getTeeTimeAvailabilitiesByDate(Date aDate) {
        List<TeeTimeAvailability> foundTeeTimes = teeTimeAvailabilityRepository.findByDate(aDate).orElse(null);

        if (foundTeeTimes == null) {
            throw new IllegalArgumentException("No tee times available for the specified date!");
        }

        return foundTeeTimes;
    }

    @Transactional
    public List<TeeTimeAvailability> getTeeTimeAvailabilitiesByCourse(String aCourse) {
        List<TeeTimeAvailability> foundTeeTimes = teeTimeAvailabilityRepository.findByCourseName(aCourse).orElse(null);

        if (foundTeeTimes == null) {
            throw new IllegalArgumentException("No tee times available for the specified course!");
        }

        return foundTeeTimes;
    }

    @Transactional
    public List<TeeTimeAvailability> getTeeTimeAvailabilitiesByNumOfGolfers(Integer aNumOfGolfers) {
        List<TeeTimeAvailability> foundTeeTimes = teeTimeAvailabilityRepository.findByNumOfGolfers(aNumOfGolfers).orElse(null);

        if (foundTeeTimes == null) {
            throw new IllegalArgumentException("No tee times available for the specified number of golfers!");
        }

        return foundTeeTimes;
    }

    @Transactional
    public List<TeeTimeAvailability> getAllTeeTimeAvailabilities(Time lowTime, Time highTime, Date lowDate, Date highDate, 
    String club, String course, Integer numOfGolfers) {
        
        List<TeeTimeAvailability> accumulatedTeeTimes = teeTimeAvailabilityRepository.findByFilters(numOfGolfers, lowTime, highTime, lowDate, highDate, club, course).orElse(null);
        
        if (accumulatedTeeTimes == null) {
            throw new IllegalArgumentException("No available tee times found with these parameters!");
        }

        return accumulatedTeeTimes;
    }

    @Transactional
    public void deleteTeeTimeAvailability(String userToken, Integer aId) {
        UserAccount foundUser = Utilities.getUserWithToken(userAccountRepository, userToken);
        
        if (foundUser == null) {
            throw new IllegalArgumentException("User not found!");
        }

        if (foundUser.getUserType().equals("CUSTOMER")) {
            throw new IllegalArgumentException("Customers cannot delete tee time availablilties!"); 
        }

        TeeTimeAvailability foundTeeTime = teeTimeAvailabilityRepository.findById(aId).orElse(null);

        if (foundTeeTime == null) {
            throw new IllegalArgumentException("Tee time availablilty not found!");
        }

        teeTimeAvailabilityRepository.delete(foundTeeTime);
    }
}
