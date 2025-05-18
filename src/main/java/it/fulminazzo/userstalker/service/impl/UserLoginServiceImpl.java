package it.fulminazzo.userstalker.service.impl;

import it.fulminazzo.userstalker.domain.dto.UserLoginCountDto;
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
import java.util.function.Supplier;

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
    public List<UserLoginCountDto> getTopUserLogins(int count) {
        return getCountedList(count, repository::findTopUserLogins);
    }

    @Override
    public List<UserLoginDto> getNewestUserLogins(int count) {
        return getCountedList(count, () -> repository.findAll(Sort.by(Sort.Order.desc("loginDate"))).stream()
                .map(mapper::entityToDto)
                .toList());
    }

    @Override
    public List<String> getUsernames() {
        return repository.findDistinctUsernames();
    }

    @Override
    public List<UserLoginDto> getUserLoginsFromUsername(String username) {
        return repository.findAllByUsername(username).stream().map(mapper::entityToDto).toList();
    }

    /**
     * Gets a list from the given supplier and slices it until the given size.
     *
     * @param count    the size of the new list. If 0, the whole list will be returned
     * @param supplier the list supplier
     * @param <T>      the type of the list members
     * @return the list
     */
    protected <T> List<T> getCountedList(int count, Supplier<List<T>> supplier) {
        if (count < 0) throw HttpRequestException.invalidSizeGreaterThan0();
        List<T> list = supplier.get();
        if (count > 0) list = list.subList(0, Math.min(count, list.size()));
        return list;
    }

}
