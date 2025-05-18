package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.mapstruct.factory.Mappers
import spock.lang.Specification

class UserLoginServiceImplTest extends Specification {

    private UserLoginRepository repository
    private UserLoginMapper mapper

    private UserLoginServiceImpl service

    void setup() {
        repository = setupRepository()
        mapper = Mappers.getMapper(UserLoginMapper)

        service = new UserLoginServiceImpl(repository, mapper)
    }

    private UserLoginRepository setupRepository() {
        def repository = Mock(UserLoginRepository)
        return repository
    }

}
