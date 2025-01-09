package joseph.ciaravella.TeeTimeFinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dto.Authentication.AuthenticationCO;
import joseph.ciaravella.TeeTimeFinder.dto.Authentication.AuthenticationDTO;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.security.JwtService;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;

@Service
public class AuthenticationService {
    
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    JwtService jwtService;

    @Transactional
    public AuthenticationDTO login(AuthenticationCO authenticationCO) {
        String email = authenticationCO.getEmail();
        String rawPassword = authenticationCO.getPassword();

        if (email.trim().isEmpty() || rawPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Email or password cannot be empty!");
        }

        UserAccount user = userAccountRepository.findUserByEmail(email).orElseThrow(
            () -> new IllegalArgumentException("User not found, please create an account!"));

        if (!verifyPassword(rawPassword, user.getPassword())) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        validateUserTypeAndAuthorities(user);

        String token = jwtService.generateToken(user);
        user.setToken(token);
        userAccountRepository.save(user);

        return new AuthenticationDTO(email, token, user.getUserType());
    }
    
    @Transactional
    public void logout(String token) {
        UserAccount user = Utilities.getUserWithToken(userAccountRepository, token);
        
        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        }
            
        user.setToken(null);
        userAccountRepository.save(user);
    }


    // helper methods

    public boolean verifyPassword(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    public void validateUserTypeAndAuthorities(UserAccount user) {
        if (user == null) {
            throw new IllegalArgumentException("User not found!");
        }

        String userType = user.getUserType();
        String authority = user.getAuthorities().stream().findFirst().map(GrantedAuthority::getAuthority).orElse("");

        switch (userType) {
            case "ADMINISTRATOR":
                if (!authority.equals("ROLE_ADMIN")) {
                    throw new IllegalStateException("Administrator account does not have the correct authorities!");
                }
                break;
            case "COURSE_ADMIN":
                if (!authority.equals("ROLE_COURSE_ADMIN")) {
                    throw new IllegalStateException("Course admin account does not have the correct authorities!");
                }
                break;
            case "CUSTOMER":
                if (!authority.equals("ROLE_CUSTOMER")) {
                    throw new IllegalStateException("Customer account does not have the correct authorities!");
                }
                break;
        
            default:
                throw new IllegalStateException("Unknown user type: " + userType);
        }
    }
}
