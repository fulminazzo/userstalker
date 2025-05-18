package it.fulminazzo.userstalker

import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification

@SpringBootTest
class UserStalkerApplicationTests extends Specification {

    def 'test context loads'() {
        expect:
        true
    }

}
