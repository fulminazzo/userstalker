package it.fulminazzo.userstalker.service.impl;

import it.fulminazzo.userstalker.domain.dto.UserLoginCount;
import it.fulminazzo.userstalker.domain.dto.UserLoginDto;
import it.fulminazzo.userstalker.domain.entity.UserLogin;
import it.fulminazzo.userstalker.exception.HttpRequestException;
import it.fulminazzo.userstalker.mapper.UserLoginMapper;
import it.fulminazzo.userstalker.repository.UserLoginRepository;
import it.fulminazzo.userstalker.service.UserLoginService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * An implementation of {@link UserLoginService}.
 */
@RequiredArgsConstructor
@Service
class UserLoginServiceImpl implements UserLoginService {

    private final UserLoginRepository repository;

    private final UserLoginMapper mapper;

    @Transactional
    @Override
    public UserLoginDto addNewUserLogin(UserLoginDto userLoginDto) {
        UserLogin userLogin = mapper.dtoToEntity(userLoginDto);
        userLogin = repository.save(userLogin);
        return mapper.entityToDto(userLogin);
    }

    @Override
    public List<UserLoginCount> getTopUserLogins(int count) {
        return List.of();
    }

    @Override
    public List<UserLoginDto> getNewestUserLogins(int count) {
        List<UserLoginDto> logins = repository.findAll(Sort.by(Sort.Order.desc("loginDate"))).stream()
                .map(mapper::entityToDto)
                .toList();
        if (count > 0) logins = logins.subList(0, Math.min(count, logins.size()));
        else if (count < 0)
            throw HttpRequestException.invalidSizeGreaterThan0();
        return logins;
    }

    @Override
    public List<String> getUsernames() {
        return repository.findDistinctUsernames();
    }

    @Override
    public List<UserLoginDto> getUserLoginsFromUsername(String username) {
        return repository.findAllByUsername(username).stream().map(mapper::entityToDto).toList();
    }

}
