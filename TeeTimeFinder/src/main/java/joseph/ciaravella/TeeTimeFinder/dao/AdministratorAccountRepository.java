package joseph.ciaravella.TeeTimeFinder.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import joseph.ciaravella.TeeTimeFinder.model.AdministratorAccount;

public interface AdministratorAccountRepository extends CrudRepository<AdministratorAccount, Integer> {
    Optional<AdministratorAccount> findByEmail(String email);
    Optional<AdministratorAccount> findByToken(String token);

}
