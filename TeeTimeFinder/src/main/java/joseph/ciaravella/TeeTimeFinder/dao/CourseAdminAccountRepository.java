package joseph.ciaravella.TeeTimeFinder.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import joseph.ciaravella.TeeTimeFinder.model.CourseAdminAccount;

public interface CourseAdminAccountRepository extends CrudRepository<CourseAdminAccount, Integer> {
    Optional<CourseAdminAccount> findByEmail(String courseAdminEmail);
    Optional<CourseAdminAccount> findByToken(String courseAdminToken);
    void deleteByEmail(String email);
}