package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.entity.UserLogin

import java.time.LocalDateTime

class UserLoginUtils {

    static final UserLogin FIRST_USER1 = UserLogin.builder()
            .id(UUID.randomUUID())
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(LocalDateTime.of(2025, 5, 18, 17, 30))
            .build()

    static final UserLogin FIRST_USER2 = UserLogin.builder()
            .id(UUID.randomUUID())
            .username('alex')
            .ip('12.223.334.45')
            .loginDate(LocalDateTime.of(2025, 5, 18, 17, 35))
            .build()

}
