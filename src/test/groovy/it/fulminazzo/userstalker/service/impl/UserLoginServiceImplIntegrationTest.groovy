package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.dto.UserLoginCountDto
import it.fulminazzo.userstalker.domain.dto.UserLoginDto
import it.fulminazzo.userstalker.domain.entity.UserLogin
import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

import java.time.LocalDateTime

import static it.fulminazzo.userstalker.service.impl.UserLoginUtils.*

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

    def 'test getTopUserLogins returns ordered list with size #size'() {
        when:
        def logins = service.getTopUserLogins(size)

        then:
        logins == expected

        where:
        size || expected
        0    || [
                new UserLoginCountDto(FIRST_USER1.username, 3),
                new UserLoginCountDto(FIRST_USER2.username, 2),
                new UserLoginCountDto(FIRST_USER3.username, 1)
        ]
        1    || [
                new UserLoginCountDto(FIRST_USER1.username, 3)
        ]
        2    || [
                new UserLoginCountDto(FIRST_USER1.username, 3),
                new UserLoginCountDto(FIRST_USER2.username, 2)
        ]
        3    || [
                new UserLoginCountDto(FIRST_USER1.username, 3),
                new UserLoginCountDto(FIRST_USER2.username, 2),
                new UserLoginCountDto(FIRST_USER3.username, 1)
        ]
        4    || [
                new UserLoginCountDto(FIRST_USER1.username, 3),
                new UserLoginCountDto(FIRST_USER2.username, 2),
                new UserLoginCountDto(FIRST_USER3.username, 1)
        ]
    }

    def 'test getTopMonthlyUserLogins returns ordered list with size #size'() {
        when:
        def logins = service.getTopMonthlyUserLogins(size)

        then:
        logins == expected

        where:
        size || expected
        0    || [
                new UserLoginCountDto(FIRST_USER1.username, 2),
                new UserLoginCountDto(FIRST_USER2.username, 1)
        ]
        1    || [
                new UserLoginCountDto(FIRST_USER1.username, 2)
        ]
        2    || [
                new UserLoginCountDto(FIRST_USER1.username, 2),
                new UserLoginCountDto(FIRST_USER2.username, 1)
        ]
        3    || [
                new UserLoginCountDto(FIRST_USER1.username, 2),
                new UserLoginCountDto(FIRST_USER2.username, 1)
        ]
    }

    def 'test getNewestUserLogins returns ordered list with size #size'() {
        when:
        def logins = service.getNewestUserLogins(size)

        then:
        logins == expected.collect { mapper.entityToDto(it) }

        where:
        size || expected
        0    || [FIRST_USER3, THIRD_USER1, SECOND_USER2, SECOND_USER1, FIRST_USER2, FIRST_USER1]
        1    || [FIRST_USER3]
        2    || [FIRST_USER3, THIRD_USER1]
        3    || [FIRST_USER3, THIRD_USER1, SECOND_USER2]
        7    || [FIRST_USER3, THIRD_USER1, SECOND_USER2, SECOND_USER1, FIRST_USER2, FIRST_USER1]
    }

    def 'test getUsernames returns all distinct usernames'() {
        when:
        def usernames = service.getUsernames()

        then:
        usernames.size() == 3
        FIRST_USER1.username in usernames
        FIRST_USER2.username in usernames
        FIRST_USER3.username in usernames
    }

    private void setupRepository() {
        repository.saveAll(UserLoginUtils.properties.values()
                .findAll { it instanceof UserLogin }
                .each { it.id = null })
    }

}
