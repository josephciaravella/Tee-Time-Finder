package joseph.ciaravella.TeeTimeFinder.service;

import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

@Service
public class UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Transactional
    public void createCustomerAccount(String aEmail, String aPassword) {
        
        if (aEmail.trim().isEmpty()){
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (aPassword.trim().isEmpty()){
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

        if (existingUserAccount != null) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        CustomerAccount customerAccount = new CustomerAccount();

        customerAccount.setEmail(aEmail);
        customerAccount.setPassword(aPassword);

        userAccountRepository.save(customerAccount);
    }

    @Transactional
    public void createCourseAdminAccount(String aEmail, String aPassword, String aAssociatedClub) {
        if (aEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (aPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        if (aAssociatedClub.trim().isEmpty()) {
            throw new IllegalArgumentException("Associated club cannot be empty");
        }

        UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

        if (existingUserAccount != null) {
            throw new IllegalArgumentException("Email is already in use");
        }

        CourseAdminAccount courseAdminAccount = new CourseAdminAccount();

        courseAdminAccount.setEmail(aEmail);
        courseAdminAccount.setPassword(aPassword);
        courseAdminAccount.setAssociatedClub(aAssociatedClub);

        userAccountRepository.save(courseAdminAccount);
    }

    @Transactional
    public void updateCustomerAccount(String oldEmail, String newEmail, String newPassword) {
        UserAccount user = userAccountRepository.findUserByEmail(oldEmail).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("No user was found with this email!");
        }

        if (newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        if (newEmail.equals(user.getEmail()) || newEmail.equals(oldEmail)) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        user.setEmail(newEmail);
        user.setPassword(newPassword);

        userAccountRepository.save(user);
    }
    
}
