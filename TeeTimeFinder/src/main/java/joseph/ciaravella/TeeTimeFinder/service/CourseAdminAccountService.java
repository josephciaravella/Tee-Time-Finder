package joseph.ciaravella.TeeTimeFinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.CourseAdminAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;



@Service
public class CourseAdminAccountService {

    @Autowired
    CourseAdminAccountRepository courseAdminAccountRepository;

    @Transactional
    public CourseAdminAccount createCourseAdminAccount(String email, String password, String associatedClub) {
        if (email == null) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        if (password == null) {
            throw new IllegalArgumentException("Password field cannot be left blank!");
        }

        if (associatedClub == null) {
            throw new IllegalArgumentException("Associated Club field cannot be left blank!");
        }

        CourseAdminAccount existingCourseAdminAccount = courseAdminAccountRepository.findByEmail(email).orElse(null);
        if (existingCourseAdminAccount != null) {
            throw new IllegalArgumentException("This email is already in use!");
        }

        CourseAdminAccount courseAdmin = new CourseAdminAccount();
        courseAdmin.setEmail(email);
        courseAdmin.setPassword(password);
        courseAdmin.setAssociatedClub(associatedClub);
        courseAdminAccountRepository.save(courseAdmin);
        return courseAdmin;
    }

    @Transactional
    public void updateCourseAdminEmail(String oldEmail, String newEmail) {
        
        if (newEmail == null) {
            throw new IllegalArgumentException("New email field cannot be left blank!");
        }

        if (newEmail.equals(oldEmail)) {
            throw new IllegalArgumentException("Old email cannot be the same as new email!");           
        }

        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByEmail(oldEmail).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        courseAdmin.setEmail(newEmail);
        courseAdminAccountRepository.save(courseAdmin);
    }

    @Transactional
    public void updateCourseAdminPassword(String token, String oldPassword, String newPassword) {
        
        if (newPassword == null) {
            throw new IllegalArgumentException("New password field cannot be left blank!");
        }

        if (newPassword.equals(oldPassword)) {
            throw new IllegalArgumentException("Old password cannot be the same as new password!");           
        }

        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByToken(token).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        courseAdmin.setPassword(newPassword);
        courseAdminAccountRepository.save(courseAdmin);
    }

    @Transactional
    public void updateCourseAdminAssociatedClub(String token, String oldClub, String newClub) {
        
        if (newClub == null) {
            throw new IllegalArgumentException("New club field cannot be left blank!");
        }

        if (newClub.equals(oldClub)) {
            throw new IllegalArgumentException("Old club cannot be the same as new club!");           
        }

        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByToken(token).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        courseAdmin.setAssociatedClub(newClub);
        courseAdminAccountRepository.save(courseAdmin);
    }

    @Transactional
    public void deleteCourseAdminByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByEmail(email).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        courseAdminAccountRepository.delete(courseAdmin);
    }

    @Transactional
    public CourseAdminAccount getCourseAdminAccountByEmail(String email) {
        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByEmail(email).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        return courseAdmin;
    }

    @Transactional
    public List<CourseAdminAccount> getAllCourseAdminAccounts() {
        return Utilities.iterableToArrayList(courseAdminAccountRepository.findAll());
    }
    
}
