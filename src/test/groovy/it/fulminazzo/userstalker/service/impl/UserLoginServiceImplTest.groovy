package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.dto.UserLoginCountDto
import it.fulminazzo.userstalker.domain.dto.UserLoginDto
import it.fulminazzo.userstalker.domain.entity.UserLogin
import it.fulminazzo.userstalker.exception.HttpRequestException
import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Sort
import spock.lang.Specification

import java.time.LocalDateTime

class UserLoginServiceImplTest extends Specification {

    private static final UserLogin FIRST_ENTITY = UserLogin.builder()
            .id(UUID.randomUUID())
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(LocalDateTime.of(2025, 5, 18, 17, 30))
            .build()

    private static final UserLogin SECOND_ENTITY = UserLogin.builder()
            .id(UUID.randomUUID())
            .username('alex')
            .ip('12.223.334.45')
            .loginDate(LocalDateTime.of(2025, 5, 18, 17, 35))
            .build()

    private UserLoginRepository repository
    private UserLoginMapper mapper

    private UserLoginServiceImpl service

    void setup() {
        repository = setupRepository()
        mapper = Mappers.getMapper(UserLoginMapper)

        service = new UserLoginServiceImpl(repository, mapper)
    }

    def 'test that addNewUserLogin calls repository#save'() {
        given:
        def dto = UserLoginDto.builder()
                .username('fulminazzo')
                .ip('11.222.333.44')
                .loginDate(LocalDateTime.of(2025, 5, 18, 17, 25))
                .build()

        when:
        service.addNewUserLogin(dto)

        then:
        1 * repository.save(_)
    }

    def 'test getTopUserLogins returns list with size #count'() {
        when:
        def logins = service.getTopUserLogins(count)

        then:
        logins == expected

        where:
        count || expected
        0     || [new UserLoginCountDto(FIRST_ENTITY.username, 2), new UserLoginCountDto(SECOND_ENTITY.username, 1)]
        1     || [new UserLoginCountDto(FIRST_ENTITY.username, 2)]
        2     || [new UserLoginCountDto(FIRST_ENTITY.username, 2), new UserLoginCountDto(SECOND_ENTITY.username, 1)]
    }

    def 'test getTopUserLogins with negative size throws'() {
        when:
        service.getTopUserLogins(-1)

        then:
        def e = thrown(HttpRequestException)
        e.status == HttpRequestException.invalidSizeGreaterThan0().status
        e.message == HttpRequestException.invalidSizeGreaterThan0().message
    }

    def 'test getTopMonthlyUserLogins returns list with size #count'() {
        when:
        def logins = service.getTopMonthlyUserLogins(count)

        then:
        logins == expected

        where:
        count || expected
        0     || [new UserLoginCountDto(FIRST_ENTITY.username, 2), new UserLoginCountDto(SECOND_ENTITY.username, 1)]
        1     || [new UserLoginCountDto(FIRST_ENTITY.username, 2)]
        2     || [new UserLoginCountDto(FIRST_ENTITY.username, 2), new UserLoginCountDto(SECOND_ENTITY.username, 1)]
    }

    def 'test getTopMonthlyUserLogins with negative size throws'() {
        when:
        service.getTopMonthlyUserLogins(-1)

        then:
        def e = thrown(HttpRequestException)
        e.status == HttpRequestException.invalidSizeGreaterThan0().status
        e.message == HttpRequestException.invalidSizeGreaterThan0().message
    }

    def 'test getNewestUserLogins returns list with size #count'() {
        when:
        def logins = service.getNewestUserLogins(count)

        then:
        logins == expected.collect { mapper.entityToDto(it) }

        where:
        count || expected
        0     || [SECOND_ENTITY, FIRST_ENTITY]
        1     || [SECOND_ENTITY]
        2     || [SECOND_ENTITY, FIRST_ENTITY]
    }

    def 'test getNewestUserLogins with negative size throws'() {
        when:
        service.getNewestUserLogins(-1)

        then:
        def e = thrown(HttpRequestException)
        e.status == HttpRequestException.invalidSizeGreaterThan0().status
        e.message == HttpRequestException.invalidSizeGreaterThan0().message
    }

    def 'test getUsernames returns usernames of entities'() {
        when:
        def usernames = service.getUsernames()

        then:
        usernames == [FIRST_ENTITY.username, SECOND_ENTITY.username]
    }

    def 'test getUserLoginsFromUsername returns only #username\'s logins'() {
        when:
        def logins = service.getUserLoginsFromUsername(username)

        then:
        logins == expected.collect { mapper.entityToDto(it) }

        where:
        username               || expected
        FIRST_ENTITY.username  || [FIRST_ENTITY]
        SECOND_ENTITY.username || [SECOND_ENTITY]
    }

    private UserLoginRepository setupRepository() {
        def userLogins = [
                new UserLoginCountDto(FIRST_ENTITY.username, 2),
                new UserLoginCountDto(SECOND_ENTITY.username, 1)
        ]
        def repository = Mock(UserLoginRepository)
        repository.findTopUserLogins() >> userLogins
        repository.findTopMonthlyUserLogins() >> userLogins
        repository.findAll(_ as Sort) >> [SECOND_ENTITY, FIRST_ENTITY]
        repository.findDistinctUsernames() >> [FIRST_ENTITY.username, SECOND_ENTITY.username]
        repository.findAllByUsername(_ as String) >> { args ->
            def username = args[0]
            if (username == FIRST_ENTITY.username) return [FIRST_ENTITY]
            else if (username == SECOND_ENTITY.username) return [SECOND_ENTITY]
            else return []
        }
        return repository
    }

}
