package joseph.ciaravella.TeeTimeFinder.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer>{
    Optional<UserAccount> findUserByEmail(String email);
    void deleteByEmail(String email);
}
