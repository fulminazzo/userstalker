package it.fulminazzo.userstalker

import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder

final class MockMvcUtils {

    static MockHttpServletRequestBuilder authenticate(MockHttpServletRequestBuilder requestBuilder) {
        return requestBuilder.header('Authorization', "Basic ${toBase64('test:test')}")
    }

    static String toBase64(String data) {
        return Base64.getEncoder().encodeToString(data.bytes)
    }

}
