package it.fulminazzo.userstalker.service.impl;

import it.fulminazzo.userstalker.domain.dto.UserLoginDto;
import it.fulminazzo.userstalker.domain.entity.UserLogin;
import it.fulminazzo.userstalker.mapper.UserLoginMapper;
import it.fulminazzo.userstalker.repository.UserLoginRepository;
import it.fulminazzo.userstalker.service.UserLoginService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<UserLoginDto> getNewestUserLogins(Integer count) {
        return List.of();
    }

    @Override
    public List<String> getUsernames() {
        return repository.findDistinctUsernames();
    }

    @Override
    public List<UserLoginDto> getUserLoginsFromUsername(String username) {
        return repository.findAllByUsername(username).stream().map(mapper::entityToDto).collect(Collectors.toList());
    }

}
