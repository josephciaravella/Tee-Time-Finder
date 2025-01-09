package joseph.ciaravella.TeeTimeFinder.service;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dto.UserAccount.UserAccountDTO;
import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;
import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;

@Service
public class UserAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthenticationService authenticationService;

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
        customerAccount.setPassword(passwordEncoder.encode(aPassword));

        userAccountRepository.save(customerAccount);
    }

    @Transactional
    public UserAccountDTO createCourseAdminAccount(String token, String aEmail, String aPassword, String aAssociatedClub) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, token);
        authenticationService.validateUserTypeAndAuthorities(user);
        if (!user.getUserType().equals("ADMINISTRATOR")) {
            throw new IllegalArgumentException("Only the administrator can create course admin accounts!");
        }
        
        if (aEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (aPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        if (aAssociatedClub.trim().isEmpty()) {
            throw new IllegalArgumentException("Associated club cannot be empty!");
        }

        UserAccount existingUserAccount = userAccountRepository.findUserByEmail(aEmail).orElse(null);

        if (existingUserAccount != null) {
            throw new IllegalArgumentException("Email is already in use!");
        }

        CourseAdminAccount courseAdminAccount = new CourseAdminAccount();

        courseAdminAccount.setEmail(aEmail);
        courseAdminAccount.setPassword(passwordEncoder.encode(aPassword));
        courseAdminAccount.setAssociatedClub(aAssociatedClub);

        userAccountRepository.save(courseAdminAccount);
        return convertToDto(courseAdminAccount);
    }

    // for updating customer & course admin account
    @Transactional
    public void updateAccount(String token, String newEmail, String newPassword) {
        UserAccount user = userAccountRepository.findUserByToken(token).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("No user was found with this token!");
        }

        if (newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (newPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty!");
        }

        if (!newEmail.equals(user.getEmail())) {
            System.out.println("New email: " + newEmail);
            UserAccount checkExistingUser = userAccountRepository.findUserByEmail(newEmail).orElse(null);
            if (checkExistingUser != null) {
                String checkExistingUserToken = checkExistingUser.getToken();
                if (checkExistingUserToken == null || !checkExistingUserToken.equals(user.getToken())) {
                    throw new IllegalArgumentException("Email already in use by someone else!");
                }
            }
        }

        user.setEmail(newEmail);
        user.setPassword(passwordEncoder.encode(newPassword));

        userAccountRepository.save(user);
    }

    // for updating customer & course admin from administrator account
    @Transactional
    public UserAccountDTO updateUserAccount(String token, String oldEmail, String newEmail, String newPassword) {
        UserAccount user = getUserByEmail(token, oldEmail);
        authenticationService.validateUserTypeAndAuthorities(user);

        if (user == null) {
            throw new IllegalArgumentException("No user was found with this email!");
        }
        
        if (!user.getUserType().equals("ADMINISTRATOR")) {
            throw new IllegalArgumentException("Only the administrator can update user accounts!");
        }

        if (newEmail == null) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (newEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty!");
        }

        if (newPassword == null || newPassword.trim().isEmpty()) {
            newPassword = user.getPassword();
        }

        UserAccount checkExistingUser = userAccountRepository.findUserByEmail(newEmail).orElse(null);

        if (checkExistingUser != null && checkExistingUser != user) {
            throw new IllegalArgumentException("This email is in use by somebody else");
        }

        user.setEmail(newEmail);
        user.setPassword(passwordEncoder.encode(newPassword));

        return convertToDto(user);
    }

    @Transactional
    public UserAccount getUserByToken(String token) {
        if (token == null) {
            throw new IllegalArgumentException("Token cannot be null!");
        }
        
        UserAccount user = userAccountRepository.findUserByToken(token).orElse(null);
        
        if (user == null) {
            throw new IllegalArgumentException("No user found!");
        }
        return user;
    }

    @Transactional
    public UserAccount getUserByEmail(String token, String email) {
        UserAccount requester = Utilities.getUserWithToken(userAccountRepository, token);
        authenticationService.validateUserTypeAndAuthorities(requester);

        if (!requester.getUserType().equals("ADMINISTRATOR")) {
            throw new IllegalArgumentException("Only the administrator has access to other user accounts!");
        }
        
        UserAccount user = userAccountRepository.findUserByEmail(email).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("No user found with the email: " + email);
        }

        return user;
    }
    
    @Transactional
    public List<UserAccount> getAllCourseAdmins() {
        List<UserAccount> foundUsers = userAccountRepository.findByUserType(Collections.singletonList(CourseAdminAccount.class)).orElse(null);

        if (foundUsers == null) {
        throw new IllegalArgumentException("No instructors found!");
        }

        return foundUsers;
    }

    @Transactional
    public List<UserAccount> getAllCustomers() {
        List<UserAccount> foundUsers = userAccountRepository.findByUserType(Collections.singletonList(CustomerAccount.class)).orElse(null);

        if (foundUsers == null) {
        throw new IllegalArgumentException("No customers found!");
        }

        return foundUsers;
    }

    @Transactional
    public void deleteAccount(String token, String email) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, token);
        authenticationService.validateUserTypeAndAuthorities(user);
        if (!user.getUserType().equals("ADMINISTRATOR") && !user.getEmail().equals(email)) {
            throw new IllegalArgumentException("Only the person to which this account belongs and the administrator can delete this account!");
        }

        UserAccount foundUser = userAccountRepository.findUserByEmail(email).orElse(null);

        if (foundUser == null) {
            throw new IllegalArgumentException("No user found with email: " + email);
        }

        userAccountRepository.delete(foundUser);
    }


    private UserAccountDTO convertToDto(UserAccount userAccount) {
        if (userAccount == null) {
          throw new IllegalArgumentException("There is no such user account!");
        }
        return new UserAccountDTO(userAccount.getUserType(), userAccount.getEmail(), userAccount.getToken());
      }
}
