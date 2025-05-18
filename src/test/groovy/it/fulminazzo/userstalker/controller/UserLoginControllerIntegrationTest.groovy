package it.fulminazzo.userstalker.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import it.fulminazzo.userstalker.MockMvcUtils
import it.fulminazzo.userstalker.domain.dto.UserLoginDto
import it.fulminazzo.userstalker.mapper.UserLoginMapper
import it.fulminazzo.userstalker.repository.UserLoginRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

import java.time.LocalDateTime
import java.util.function.Consumer

import static it.fulminazzo.userstalker.service.impl.UserLoginUtils.*

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class UserLoginControllerIntegrationTest extends Specification {
    
    private static final String ENDPOINT = '/api/v1/userlogins'

    @Autowired
    private UserLoginRepository repository

    @Autowired
    private UserLoginMapper userLoginMapper

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
        def userLoginDto = createUserLogin(null)
        def json = jsonMapper.writeValueAsString(userLoginDto)

        when:
        def response = mockMvc.perform(
                MockMvcUtils.authenticate(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isCreated(),
                MockMvcResultMatchers.content().json(json)
        )
    }

    def 'test postUserLogin returns 400 on invalid data #data'() {
        given:
        def json = jsonMapper.writeValueAsString(data)

        when:
        def response = mockMvc.perform(
                MockMvcUtils.authenticate(MockMvcRequestBuilders.post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isBadRequest(),
                MockMvcResultMatchers.jsonPath('status').value(HttpStatus.BAD_REQUEST.value()),
                MockMvcResultMatchers.jsonPath('error').isString()
        )

        where:
        data << [
                createUserLogin(u -> u.username = null),
                createUserLogin(u -> u.username = 'aa'),
                createUserLogin(u -> u.username = 'aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa'),
                createUserLogin(u -> u.ip = null),
                createUserLogin(u -> u.ip = ''),
                createUserLogin(u -> u.loginDate = null),
                createUserLogin(u -> u.loginDate = LocalDateTime.of(3010, 2, 2, 1, 1)),
        ]
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
        given:
        def expectedJson = jsonMapper.writeValueAsString(expected
                .collect { userLoginMapper.entityToDto(it) }
        )

        and:
        def url = "$ENDPOINT/newest"
        if (size > 0) url += "?count=${size}"

        when:
        def response = mockMvc.perform(
                MockMvcUtils.authenticate(MockMvcRequestBuilders.get(url))
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().json(expectedJson)
        )

        where:
        size || expected
        0    || [FIRST_USER3, THIRD_USER1, SECOND_USER2, SECOND_USER1, FIRST_USER2, FIRST_USER1]
        1    || [FIRST_USER3]
        2    || [FIRST_USER3, THIRD_USER1]
        3    || [FIRST_USER3, THIRD_USER1, SECOND_USER2]
        7    || [FIRST_USER3, THIRD_USER1, SECOND_USER2, SECOND_USER1, FIRST_USER2, FIRST_USER1]
    }

    def 'test getUsernames returns all usernames'() {
        when:
        def response = mockMvc.perform(
                MockMvcUtils.authenticate(MockMvcRequestBuilders.get("$ENDPOINT/usernames"))
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.jsonPath('[0]').value(FIRST_USER2.username),
                MockMvcResultMatchers.jsonPath('[1]').value(FIRST_USER1.username),
                MockMvcResultMatchers.jsonPath('[2]').value(FIRST_USER3.username),
        )
    }

    def 'test getUserLoginsFromUsername of username #username returns expected list'() {
        given:
        def expectedJson = jsonMapper.writeValueAsString(expected
                .collect { userLoginMapper.entityToDto(it) }
        )

        when:
        def response = mockMvc.perform(
                MockMvcUtils.authenticate(MockMvcRequestBuilders.get("$ENDPOINT/$username"))
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isOk(),
                MockMvcResultMatchers.content().json(expectedJson)
        )

        where:
        username             || expected
        FIRST_USER1.username || [FIRST_USER1, SECOND_USER1, THIRD_USER1]
        FIRST_USER2.username || [FIRST_USER2, SECOND_USER2]
        FIRST_USER3.username || [FIRST_USER3]
    }

    private static UserLoginDto createUserLogin(Consumer<UserLoginDto> function) {
        def dto = UserLoginDto.builder()
                .username('fulminazzo')
                .ip('99.888.777.66')
                .loginDate(LocalDateTime.of(2025, 5, 18, 19, 00, 25))
                .build()
        if (function != null) function.accept(dto)
        return dto
    }

}
