package joseph.ciaravella.TeeTimeFinder.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import joseph.ciaravella.TeeTimeFinder.model.UserAccount;

public interface UserAccountRepository extends CrudRepository<UserAccount, Integer>{
    Optional<UserAccount> findUserByEmail(String email);
    Optional<UserAccount> findUserByToken(String token);
    @Query("SELECT u FROM UserAccount u WHERE TYPE(u) IN :types")
    Optional<List<UserAccount>> findByUserType(@Param("types") List<Class<?>> types);
    void deleteByEmail(String email);
}
