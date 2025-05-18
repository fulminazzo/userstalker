package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.entity.UserLogin
import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.mapstruct.factory.Mappers
import org.springframework.data.domain.Sort
import spock.lang.Specification

import java.time.LocalDateTime

class UserLoginServiceImplTest extends Specification {

    private final UserLogin firstEntity = UserLogin.builder()
            .id(UUID.randomUUID())
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(LocalDateTime.of(2025, 5, 18, 17, 30))
            .build()

    private final UserLogin secondEntity = UserLogin.builder()
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

    def 'test getUsernames returns usernames of entities'() {
        when:
        def usernames = service.getUsernames()

        then:
        usernames == [firstEntity.username, secondEntity.username]
    }

    private UserLoginRepository setupRepository() {
        def repository = Mock(UserLoginRepository)
        repository.findAll(_ as Sort) >> [secondEntity, firstEntity]
        repository.findDistinctUsernames() >> [firstEntity.username, secondEntity.username]
        repository.findAllByUsername(_ as String) >> { args ->
            def username = args[0]
            if (username == firstEntity.username) return [firstEntity]
            else if (username == secondEntity.username) return [secondEntity]
            else return []
        }
        return repository
    }

}
