package it.fulminazzo.userstalker.service.impl;

import it.fulminazzo.userstalker.domain.dto.UserLoginDto;
import it.fulminazzo.userstalker.mapper.UserLoginMapper;
import it.fulminazzo.userstalker.repository.UserLoginRepository;
import it.fulminazzo.userstalker.service.UserLoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository repository;

    private final UserLoginMapper mapper;

    @Override
    public UserLoginDto addNewUserLogin(UserLoginDto userLoginDto) {
        return null;
    }

    @Override
    public List<UserLoginDto> getNewestUserLogins(Integer count) {
        return List.of();
    }

    @Override
    public List<String> getUsernames() {
        return List.of();
    }

    @Override
    public List<UserLoginDto> getUserLoginsFromUsername(String username) {
        return List.of();
    }

}
