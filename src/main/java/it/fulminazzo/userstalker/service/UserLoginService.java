package it.fulminazzo.userstalker.service;

import it.fulminazzo.userstalker.domain.dto.UserLoginDto;

import java.util.List;

public interface UserLoginService {

    UserLoginDto addNewUserLogin(UserLoginDto userLoginDto);

    List<UserLoginDto> getTopUserLogins(Integer count);

    List<UserLoginDto> getTopMonthlyUserLogins(Integer count);

    List<UserLoginDto> getNewestUserLogins(Integer count);

    List<String> getUsernames();

    List<UserLoginDto> getUserLoginsFromUsername(String username);

}
