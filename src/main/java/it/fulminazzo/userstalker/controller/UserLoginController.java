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

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/api/userlogins")
public class UserLoginController {

    private final UserLoginService service;

    @PostMapping
    public ResponseEntity<?> postUserLogin(@Valid @RequestBody UserLoginDto userLoginDto) {
        userLoginDto = service.addNewUserLogin(userLoginDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(userLoginDto);
    }

    @GetMapping("/top")
    public List<UserLoginCountDto> getTopUserLogins(@RequestParam int count) {
        return service.getTopUserLogins(count);
    }

    @GetMapping("/month")
    public List<UserLoginCountDto> getMonthlyUserLogins(@RequestParam int count) {
        return service.getTopMonthlyUserLogins(count);
    }

    @GetMapping("/newest")
    public List<UserLoginDto> getNewestUserLogins(@RequestParam int count) {
        return service.getNewestUserLogins(count);
    }

    @GetMapping("/usernames")
    public List<String> getUsernames() {
        return service.getUsernames();
    }

    @GetMapping("/{username}")
    public List<UserLoginDto> getUserLogins(@PathVariable String username) {
        return service.getUserLoginsFromUsername(username);
    }

}
