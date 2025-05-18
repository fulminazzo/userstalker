package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.dto.UserLoginDto
import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import java.time.LocalDateTime

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class UserLoginServiceImplIntegrationTest extends Specification {

    @Autowired
    private UserLoginRepository repository

    @Autowired
    private UserLoginMapper mapper

    private UserLoginServiceImpl service

    void setup() {
        service = new UserLoginServiceImpl(repository, mapper)
        setupRepository()
    }

    def 'test that addNewUserLogin adds user'() {
        given:
        def userLoginDto = UserLoginDto.builder()
                .username('fulminazzo')
                .ip('99.888.777.66')
                .loginDate(LocalDateTime.of(2025, 5, 18, 19, 00, 25))
                .build()

        when:
        def returnedDto = service.addNewUserLogin(userLoginDto)
        def entity = repository.findAll().find { it.ip == userLoginDto.ip }

        then:
        returnedDto.username == userLoginDto.username
        returnedDto.ip == userLoginDto.ip
        returnedDto.loginDate == userLoginDto.loginDate
        entity != null
        entity.username == userLoginDto.username
        entity.ip == userLoginDto.ip
        entity.loginDate == userLoginDto.loginDate
    }

    def 'test getUsernames returns all distinct usernames'() {
        when:
        def usernames = service.getUsernames()

        then:
        usernames == [UserLoginUtils.FIRST_USER2.username, UserLoginUtils.FIRST_USER1.username]
    }

    private void setupRepository() {
        repository.save(UserLoginUtils.FIRST_USER1)
        repository.save(UserLoginUtils.SECOND_USER1)
        repository.save(UserLoginUtils.THIRD_USER1)
        repository.save(UserLoginUtils.FIRST_USER2)
        repository.save(UserLoginUtils.SECOND_USER2)
    }

}
