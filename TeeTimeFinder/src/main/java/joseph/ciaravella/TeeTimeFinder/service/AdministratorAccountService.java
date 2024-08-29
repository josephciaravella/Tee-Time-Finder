package joseph.ciaravella.TeeTimeFinder.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import joseph.ciaravella.TeeTimeFinder.dao.AdministratorAccountRepository;
import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.AdministratorAccount;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;
import joseph.ciaravella.TeeTimeFinder.utilities.Utilities;

@Service
public class AdministratorAccountService {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    AdministratorAccountRepository administratorAccountRepository;

    @Transactional
    public void updateAdministratorEmail(String token, String newEmail) {
        AdministratorAccount user = administratorAccountRepository.findByToken(token).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("There is no administrator account!");
        }

        if (newEmail.equals(user.getEmail())) {
            throw new IllegalArgumentException("New email cannot match the old email!");
        }

        user.setEmail(newEmail);
        administratorAccountRepository.save(user);
    }

    @Transactional
    public void updateAdministratorPassword(String token, String currPassword, String newPassword) {
        AdministratorAccount user = administratorAccountRepository.findByToken(token).orElse(null);

        if (user == null) {
            throw new IllegalArgumentException("There is no administrator account!");
        }

        if (!currPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("Incorrect old password");
        }

        if (newPassword.equals(user.getPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as the old password");
        }

        user.setEmail(newPassword);
        administratorAccountRepository.save(user);
    }

    @Transactional
    public AdministratorAccount getAdministratorAccountByEmail(String email) {
        return administratorAccountRepository.findByEmail(email).orElse(null);
    }

    @Transactional
    public List<UserAccount> getAllUserAccounts() {
        return Utilities.iterableToArrayList(userAccountRepository.findAll());
    }
    
}
