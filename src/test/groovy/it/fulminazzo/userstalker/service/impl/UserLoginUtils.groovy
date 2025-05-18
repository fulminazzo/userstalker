package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.entity.UserLogin

import java.time.LocalDateTime

class UserLoginUtils {

    static final UserLogin FIRST_USER1 = UserLogin.builder()
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(LocalDateTime.of(2025, 4, 18, 17, 30))
            .build()

    static final UserLogin SECOND_USER1 = UserLogin.builder()
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(LocalDateTime.of(2025, 5, 19, 00, 30))
            .build()

    static final UserLogin THIRD_USER1 = UserLogin.builder()
            .username('fulminazzo')
            .ip('12.232.343.54')
            .loginDate(LocalDateTime.of(2025, 5, 20, 00, 30))
            .build()

    static final UserLogin FIRST_USER2 = UserLogin.builder()
            .username('alex')
            .ip('12.223.334.45')
            .loginDate(LocalDateTime.of(2025, 4, 18, 17, 35))
            .build()

    static final UserLogin SECOND_USER2 = UserLogin.builder()
            .username('alex')
            .ip('22.443.564.45')
            .loginDate(LocalDateTime.of(2025, 5, 19, 00, 35))
            .build()

}
