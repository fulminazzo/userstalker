package it.fulminazzo.userstalker.controller;

import it.fulminazzo.userstalker.domain.dto.UserLoginCountDto;
import it.fulminazzo.userstalker.domain.dto.UserLoginDto;
import it.fulminazzo.userstalker.service.UserLoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * The controller responsible for the endpoint: <code>/api/v1/userlogins</code>
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/userlogins")
public class UserLoginController {

    private final UserLoginService service;

    /**
     * Response for a POST request.
     * Creates a new user login in the database.
     *
     * @param userLoginDto the user login dto
     * @return the user login dto
     */
    @PostMapping
    public ResponseEntity<?> postUserLogin(@Valid @RequestBody UserLoginDto userLoginDto) {
        userLoginDto = service.addNewUserLogin(userLoginDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userLoginDto);
    }

    /**
     * Gets the top user logins per count.
     *
     * @param count if specified, the list will be limited to the given amount
     * @return the user logins
     */
    @GetMapping("/top")
    public List<UserLoginCountDto> getTopUserLogins(@RequestParam int count) {
        return service.getTopUserLogins(count);
    }

    /**
     * Gets the top monthly user logins per count.
     *
     * @param count if specified, the list will be limited to the given amount
     * @return the user logins
     */
    @GetMapping("/month")
    public List<UserLoginCountDto> getMonthlyUserLogins(@RequestParam int count) {
        return service.getTopMonthlyUserLogins(count);
    }

    /**
     * Gets the newest user logins.
     *
     * @param count if specified, the list will be limited to the given amount
     * @return the newest user logins
     */
    @GetMapping("/newest")
    public List<UserLoginDto> getNewestUserLogins(@RequestParam int count) {
        return service.getNewestUserLogins(count);
    }

    /**
     * Gets all the registered usernames.
     *
     * @return the usernames
     */
    @GetMapping("/usernames")
    public List<String> getUsernames() {
        return service.getUsernames();
    }

    /**
     * Gets all the user logins of the given username.
     *
     * @param username the username
     * @return the user logins
     */
    @GetMapping("/{username}")
    public List<UserLoginDto> getUserLogins(@PathVariable String username) {
        return service.getUserLoginsFromUsername(username);
    }

}
