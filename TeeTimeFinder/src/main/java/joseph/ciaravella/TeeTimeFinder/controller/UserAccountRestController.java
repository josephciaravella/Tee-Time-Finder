package joseph.ciaravella.TeeTimeFinder.controller;

import java.util.List;

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

import joseph.ciaravella.TeeTimeFinder.dto.UserAccount.CourseAdminDTO;
import joseph.ciaravella.TeeTimeFinder.dto.UserAccount.UserAccountCO;
import joseph.ciaravella.TeeTimeFinder.dto.UserAccount.UserAccountDTO;
import joseph.ciaravella.TeeTimeFinder.dto.UserAccount.CourseAdminAccount.CourseAdminAccountCO;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.service.UserAccountService;

@CrossOrigin(origins = "*")
@RestController
public class UserAccountRestController {
    
    @Autowired
    private UserAccountService userAccountService;

    
    @PostMapping("/account/createCustomer")
    public ResponseEntity<?> createCustomerAccount(@RequestBody UserAccountCO userAccountCO) {
        try {
            String email = userAccountCO.getEmail();
            String password = userAccountCO.getPassword();
            userAccountService.createCustomerAccount(email, password);
            //return response entity with status code 201 and success message
            return ResponseEntity.status(HttpStatus.CREATED).body("Customer account created successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // course admin account has an extra field we need to account for
    @PostMapping(value = {"/accounts/createCourseAdmin"})
    public ResponseEntity<?> createCourseAdminAccount(@RequestHeader String userToken, @RequestBody CourseAdminAccountCO courseAdminAccountCO) {
        try {
            String email = courseAdminAccountCO.getEmail();
            String password = courseAdminAccountCO.getPassword();
            String associatedClub = courseAdminAccountCO.getAssociatedClub();
            UserAccountDTO newCourseAdmin = userAccountService.createCourseAdminAccount(userToken, email, password, associatedClub);
            return ResponseEntity.status(HttpStatus.CREATED).body(newCourseAdmin);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    //customer & course admin account update
    @PutMapping(value = {"/account/update"})
    public ResponseEntity<?> updateAccount(@RequestHeader String userToken, @RequestBody UserAccountCO userAccountCO) {
        try {
            String email = userAccountCO.getEmail();
            String password = userAccountCO.getPassword();
            userAccountService.updateAccount(userToken, email, password);
            // return response entity with status code 200 and success message
            return ResponseEntity.ok().body("Account updated successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    //update from administrator account
    @PutMapping(value = {"/accounts/updateUserAccounts"})
    public ResponseEntity<?> updateUserAccount(@RequestHeader String userToken, @RequestParam String currEmail, @RequestBody UserAccountCO userAccountCO) {
        try {
            String email = userAccountCO.getEmail();
            String password = userAccountCO.getPassword();
            UserAccountDTO updatedUser = userAccountService.updateUserAccount(userToken, currEmail, email, password);
            // return response entity with status code 200 and updated user account
            return ResponseEntity.ok().body(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {"/accounts/getUserAccount"})
    public ResponseEntity<?> getUserAccount(@RequestHeader String userToken) {
        try {
            UserAccount user = userAccountService.getUserByToken(userToken);
            String email = user.getEmail();
            String userType = user.getUserType();
            UserAccountDTO userAccountDTO = new UserAccountDTO(userType, email, userToken);
            // return response entity with status code 200 and user account
            return ResponseEntity.ok().body(userAccountDTO);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping(value = {"/accounts/getCourseAdminAccounts"})
    public ResponseEntity<?> getCourseAdminAccounts() {
        try {
            List<UserAccount> courseAdmins = userAccountService.getAllCourseAdmins();
            // convert list of course admins to list of course admin DTOs
            List<CourseAdminDTO> courseAdminDTOs = courseAdmins.stream().map(courseAdmin -> new CourseAdminDTO(courseAdmin)).toList();
            return ResponseEntity.ok().body(courseAdminDTOs);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping(value = {"/accounts/deleteUserAccount"})
    public ResponseEntity<?> deleteAccount(@RequestHeader String userToken, @RequestParam String email) {   
        try {
            userAccountService.deleteAccount(userToken, email);
            // return response entity with status code 200 and success message
            return ResponseEntity.ok().body("Account deleted successfully!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
