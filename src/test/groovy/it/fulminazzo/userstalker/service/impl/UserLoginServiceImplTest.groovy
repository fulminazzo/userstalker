package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.entity.UserLogin
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
        def repository = Mock(UserLoginRepository)
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
