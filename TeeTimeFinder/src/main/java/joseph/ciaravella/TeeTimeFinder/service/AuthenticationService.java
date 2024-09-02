package joseph.ciaravella.TeeTimeFinder.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dto.Authentication.AuthenticationDTO;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.utilities.TokenProvider;

@Service
public class AuthenticationService {
    
    @Autowired
    private TokenProvider tokenProvider;
    
    @Autowired
    private UserAccountRepository userAccountRepository;

    @Transactional
    public AuthenticationDTO login(String email, String password) {
        UserAccount existingUser = userAccountRepository.findUserByEmail(email).orElse(null);

        if (existingUser == null) {
            throw new IllegalArgumentException("You must make an account before attempting to login!");
        }

        if (!existingUser.getPassword().equals(password)) {
            throw new IllegalArgumentException("Incorrect password!");
        }

        String generatedToken = tokenProvider.generateToken(email);
        existingUser.setToken(generatedToken);
        userAccountRepository.save(existingUser);

        AuthenticationDTO authenticated = new AuthenticationDTO();
        authenticated.setEmail(existingUser.getEmail());
        authenticated.setToken(existingUser.getToken());
        authenticated.setRole(existingUser.getUserType());

        return authenticated;
    }

    @Transactional
    public void logout(String token) {
      UserAccount existingUser = userAccountRepository.findUserByToken(token).orElse(null);
  
      if (existingUser == null) {
        throw new IllegalArgumentException("User not found");
      }
  
      existingUser.setToken(null);
      userAccountRepository.save(existingUser);
    }
}
