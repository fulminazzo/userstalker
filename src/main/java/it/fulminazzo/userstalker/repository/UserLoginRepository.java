package it.fulminazzo.userstalker.repository;

import it.fulminazzo.userstalker.domain.dto.UserLoginCountDto;
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
     * Returns an ordered list containing the number of logins a user issued to the server.
     *
     * @return the list
     */
    @Query("SELECT DISTINCT NEW it.fulminazzo.userstalker.domain.dto.UserLoginCountDto(u.username, count(*) as loginCount) " +
            "FROM UserLogin as u " +
            "GROUP BY u.username " +
            "ORDER BY loginCount DESC")
    List<UserLoginCountDto> findTopUserLogins();

    /**
     * Returns an ordered list containing the number of logins a user issued to the server during the current month.
     *
     * @return the list
     */
    @Query("SELECT DISTINCT NEW it.fulminazzo.userstalker.domain.dto.UserLoginCountDto(u.username, count(*) as loginCount) " +
            "FROM UserLogin as u " +
            "WHERE YEAR(u.loginDate) = YEAR(CURRENT_DATE) " +
            "AND MONTH(u.loginDate) = MONTH(CURRENT_DATE) " +
            "GROUP BY u.username " +
            "ORDER BY loginCount DESC")
    List<UserLoginCountDto> findTopMonthlyUserLogins();

    /**
     * Returns a distinct list containing all the usernames of the {@link UserLogin}s.
     *
     * @return the list
     */
    @Query("SELECT DISTINCT u.username FROM UserLogin as u ORDER BY u.username")
    List<String> findDistinctUsernames();

    /**
     * Returns all the {@link UserLogin}s of the given username.
     *
     * @param username the username
     * @return the list
     */
    List<UserLogin> findAllByUsername(String username);

}
