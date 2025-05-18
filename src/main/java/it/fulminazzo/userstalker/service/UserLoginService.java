package it.fulminazzo.userstalker.service;

import it.fulminazzo.userstalker.domain.dto.UserLoginCountDto;
import it.fulminazzo.userstalker.domain.dto.UserLoginDto;

import java.util.List;

/**
 * The service provider for {@link it.fulminazzo.userstalker.domain.entity.UserLogin}.
 */
public interface UserLoginService {

    /**
     * Adds a new user login.
     *
     * @param userLoginDto the user login dto
     * @return the user login dto
     */
    UserLoginDto addNewUserLogin(UserLoginDto userLoginDto);

    /**
     * Gets a list containing the user logins sorted by number of logins.
     *
     * @param count if not 0, the list will be limited to the specified count
     * @return the list of user logins count
     */
    List<UserLoginCountDto> getTopUserLogins(int count);

    /**
     * Gets a list containing the user logins of the current month sorted by number of logins.
     *
     * @param count if not 0, the list will be limited to the specified count
     * @return the list of user logins count
     */
    List<UserLoginCountDto> getTopMonthlyUserLogins(int count);

    /**
     * Gets a list containing the user logins sorted by newest.
     *
     * @param count if not 0, the list will be limited to the specified count
     * @return the list of user logins
     */
    List<UserLoginDto> getNewestUserLogins(int count);

    /**
     * Gets all the usernames that have accessed the service.
     *
     * @return the usernames
     */
    List<String> getUsernames();

    /**
     * Gets all the user logins from the given username.
     *
     * @param username the username
     * @return the user logins
     */
    List<UserLoginDto> getUserLoginsFromUsername(String username);

}
