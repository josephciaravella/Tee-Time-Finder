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
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        if (password.trim().isEmpty()) {
            throw new IllegalArgumentException("Password field cannot be left blank!");
        }

        if (associatedClub.trim().isEmpty()) {
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
    public void updateCourseAdminEmail(String newEmail, String token) {
        
        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByToken(token).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        if (newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("New email field cannot be left blank!");
        }

        if (newEmail.equals(courseAdmin.getEmail())) {
            throw new IllegalArgumentException("New email cannot be the same as the current email!");           
        }

        CourseAdminAccount existingCourseAdmin = courseAdminAccountRepository.findByEmail(newEmail).orElse(null);
        if (existingCourseAdmin != null) {
            throw new IllegalArgumentException("This email is already in use!");
        }

        courseAdmin.setEmail(newEmail);
        courseAdminAccountRepository.save(courseAdmin);
    }

    @Transactional
    public void updateCourseAdminPassword(String token, String newPassword) {
        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByToken(token).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        if (newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("New password field cannot be left blank!");
        }

        if (newPassword.equals(courseAdmin.getPassword())) {
            throw new IllegalArgumentException("Old password cannot be the same as new password!");           
        }

        courseAdmin.setPassword(newPassword);
        courseAdminAccountRepository.save(courseAdmin);
    }

    @Transactional
    public void updateCourseAdminAssociatedClub(String token, String newClub) {
        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByToken(token).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        if (newClub.trim().isEmpty()) {
            throw new IllegalArgumentException("New club field cannot be left blank!");
        }

        if (newClub.equals(courseAdmin.getAssociatedClub())) {
            throw new IllegalArgumentException("Old club cannot be the same as new club!");           
        }

        courseAdmin.setAssociatedClub(newClub);
        courseAdminAccountRepository.save(courseAdmin);
    }

    @Transactional
    public void deleteCourseAdminByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByEmail(email).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        courseAdminAccountRepository.delete(courseAdmin);
    }

    @Transactional
    public void deleteCourseAdminByToken(String token) {
        if (token.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }

        CourseAdminAccount courseAdmin = courseAdminAccountRepository.findByToken(token).orElse(null);
        if (courseAdmin == null) {
            throw new IllegalArgumentException("No course admin found with this email!");
        }

        courseAdminAccountRepository.delete(courseAdmin);
    }

    @Transactional
    public CourseAdminAccount getCourseAdminAccountByEmail(String email) {
        if (email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email field cannot be left blank!");
        }
        
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
