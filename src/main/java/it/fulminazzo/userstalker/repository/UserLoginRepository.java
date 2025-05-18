package it.fulminazzo.userstalker.repository;

import it.fulminazzo.userstalker.domain.entity.UserLogin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

/**
 * The repository associated with {@link UserLogin}.
 */
@Repository
public interface UserLoginRepository extends JpaRepository<UserLogin, UUID> {

    /**
     * Returns a distinct list containing all the usernames of the {@link UserLogin}s.
     *
     * @return the list
     */
    @Query("SELECT DISTINCT u.username FROM user_logins as u")
    List<String> findDistinctUsernames();

}
