package it.fulminazzo.userstalker.config

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpStatus
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import spock.lang.Specification

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
class SecurityConfigTest extends Specification {

    @Autowired
    private MockMvc mockMvc

    def 'test that access to page #page is permitted'() {
        when:
        def response = mockMvc.perform(
                MockMvcRequestBuilders.get(page)
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().is(expected.value())
        )

        where:
        page               || expected
        '/login'           || HttpStatus.NOT_FOUND
        '/logout'          || HttpStatus.NOT_FOUND
        '/css/test.css'    || HttpStatus.NOT_FOUND
        '/scripts/test.js' || HttpStatus.NOT_FOUND
        '/images/test.png' || HttpStatus.NOT_FOUND
        '/images/test.jpg' || HttpStatus.NOT_FOUND
    }

    def 'test that access to any page not listed is not permitted'() {
        when:
        def response = mockMvc.perform(
                MockMvcRequestBuilders.get('/index')
        )

        then:
        response.andExpectAll(
                MockMvcResultMatchers.status().isFound(),
                MockMvcResultMatchers.redirectedUrl('http://localhost/login')
        )
    }

}
