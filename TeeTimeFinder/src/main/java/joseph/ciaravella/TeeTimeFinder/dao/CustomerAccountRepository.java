package joseph.ciaravella.TeeTimeFinder.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import joseph.ciaravella.TeeTimeFinder.model.CustomerAccount;

public interface CustomerAccountRepository extends CrudRepository<CustomerAccount, Integer> {
    Optional<CustomerAccount> findByEmail(String customerEmail);
    void deleteByEmail(String email);
}
