package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import spock.lang.Specification

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
    }

}
