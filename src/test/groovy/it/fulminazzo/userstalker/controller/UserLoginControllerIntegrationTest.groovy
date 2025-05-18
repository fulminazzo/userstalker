package it.fulminazzo.userstalker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import it.fulminazzo.userstalker.MockMvcUtils
import it.fulminazzo.userstalker.domain.dto.UserLoginDto
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import java.time.LocalDateTime

import static it.fulminazzo.userstalker.service.impl.UserLoginUtils.*

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UserLoginControllerIntegrationTest extends Specification {

    @Autowired
    private UserLoginRepository repository

    @Autowired
    private MockMvc mockMvc

    private ObjectMapper jsonMapper

    @Autowired
    private UserLoginController controller

    void setup() {
        setupRepository(repository)

        jsonMapper = new ObjectMapper()
        jsonMapper.registerModule(new JavaTimeModule())
        jsonMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    }

    def 'test postUserLogin returns created on valid data'() {
        given:
        def userLoginDto = UserLoginDto.builder()
                .username('fulminazzo')
                .ip('99.888.777.66')
                .loginDate(LocalDateTime.of(2025, 5, 18, 19, 00, 25))
                .build()
        def json = jsonMapper.writeValueAsString(userLoginDto)

        when:
        def response = mockMvc.perform(
                MockMvcUtils.authenticate(MockMvcRequestBuilders.post('/v1/api/userlogins')
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().json(json)
        )
    }

    def 'test getTopUserLogins returns ordered list with size #size'() {
//        when:
//        def logins = service.getTopUserLogins(size)
//
//        then:
//        logins == expected
//
//        where:
//        size || expected
//        0    || [
//                new UserLoginCountDto(FIRST_USER1.username, 3),
//                new UserLoginCountDto(FIRST_USER2.username, 2),
//                new UserLoginCountDto(FIRST_USER3.username, 1)
//        ]
//        1    || [
//                new UserLoginCountDto(FIRST_USER1.username, 3)
//        ]
//        2    || [
//                new UserLoginCountDto(FIRST_USER1.username, 3),
//                new UserLoginCountDto(FIRST_USER2.username, 2)
//        ]
//        3    || [
//                new UserLoginCountDto(FIRST_USER1.username, 3),
//                new UserLoginCountDto(FIRST_USER2.username, 2),
//                new UserLoginCountDto(FIRST_USER3.username, 1)
//        ]
//        4    || [
//                new UserLoginCountDto(FIRST_USER1.username, 3),
//                new UserLoginCountDto(FIRST_USER2.username, 2),
//                new UserLoginCountDto(FIRST_USER3.username, 1)
//        ]
    }

    def 'test getTopMonthlyUserLogins returns ordered list with size #size'() {
//        when:
//        def logins = service.getTopMonthlyUserLogins(size)
//
//        then:
//        logins == expected
//
//        where:
//        size || expected
//        0    || [
//                new UserLoginCountDto(FIRST_USER1.username, 2),
//                new UserLoginCountDto(FIRST_USER2.username, 1)
//        ]
//        1    || [
//                new UserLoginCountDto(FIRST_USER1.username, 2)
//        ]
//        2    || [
//                new UserLoginCountDto(FIRST_USER1.username, 2),
//                new UserLoginCountDto(FIRST_USER2.username, 1)
//        ]
//        3    || [
//                new UserLoginCountDto(FIRST_USER1.username, 2),
//                new UserLoginCountDto(FIRST_USER2.username, 1)
//        ]
    }

    def 'test getNewestUserLogins returns ordered list with size #size'() {
//        when:
//        def logins = service.getNewestUserLogins(size)
//
//        then:
//        logins == expected.collect { mapper.entityToDto(it) }
//
//        where:
//        size || expected
//        0    || [FIRST_USER3, THIRD_USER1, SECOND_USER2, SECOND_USER1, FIRST_USER2, FIRST_USER1]
//        1    || [FIRST_USER3]
//        2    || [FIRST_USER3, THIRD_USER1]
//        3    || [FIRST_USER3, THIRD_USER1, SECOND_USER2]
//        7    || [FIRST_USER3, THIRD_USER1, SECOND_USER2, SECOND_USER1, FIRST_USER2, FIRST_USER1]
    }

    def 'test getUsernames returns all distinct usernames'() {
//        when:
//        def usernames = service.getUsernames()
//
//        then:
//        usernames.size() == 3
//        FIRST_USER1.username in usernames
//        FIRST_USER2.username in usernames
//        FIRST_USER3.username in usernames
    }

    def 'test getUserLoginsFromUsername of username #username returns expected list'() {
//        when:
//        def logins = service.getUserLoginsFromUsername(username)
//
//        then:
//        logins.containsAll(expected.collect { mapper.entityToDto(it) })
//
//        where:
//        username             || expected
//        FIRST_USER1.username || [FIRST_USER1, SECOND_USER1, THIRD_USER1]
//        FIRST_USER2.username || [FIRST_USER2, SECOND_USER2]
//        FIRST_USER3.username || [FIRST_USER3]
    }

}
