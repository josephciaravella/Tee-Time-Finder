package joseph.ciaravella.TeeTimeFinder.utilities;

import joseph.ciaravella.TeeTimeFinder.dao.UserAccountRepository;
import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

public class Utilities {

    public static UserAccount getUserWithToken(UserAccountRepository userAccountRepository, String token) {
        UserAccount user = userAccountRepository.findUserByToken(token).orElse(null);
        if (user == null) {
            throw new IllegalArgumentException("No user found");
        }
        return user;
    }

}
