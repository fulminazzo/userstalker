package it.fulminazzo.userstalker.service.impl

import it.fulminazzo.userstalker.domain.entity.UserLogin

import java.time.LocalDateTime

class UserLoginUtils {

    static final UserLogin FIRST_USER1 = UserLogin.builder()
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(newDateTime(-1, 18, 17, 30))
            .build()

    static final UserLogin SECOND_USER1 = UserLogin.builder()
            .username('fulminazzo')
            .ip('11.222.333.44')
            .loginDate(newDateTime(0, 19, 00, 30))
            .build()

    static final UserLogin THIRD_USER1 = UserLogin.builder()
            .username('fulminazzo')
            .ip('12.232.343.54')
            .loginDate(newDateTime(0, 20, 00, 30))
            .build()

    static final UserLogin FIRST_USER2 = UserLogin.builder()
            .username('alex')
            .ip('12.223.334.45')
            .loginDate(newDateTime(-1, 18, 17, 35))
            .build()

    static final UserLogin SECOND_USER2 = UserLogin.builder()
            .username('alex')
            .ip('22.443.564.45')
            .loginDate(newDateTime(0, 19, 00, 35))
            .build()

    static final UserLogin FIRST_USER3 = UserLogin.builder()
            .username('steve')
            .ip('00.111.000.11')
            .loginDate(newDateTime(1, 1, 12, 35))
            .build()

    static LocalDateTime newDateTime(int monthOffset, int dayOfMonth, int hour, int minute) {
        def now = LocalDateTime.now()
        def year = now.year
        def month = now.month.value + monthOffset
        if (month <= 0) {
            month += 12
            year--
        }
        return LocalDateTime.of(year, month, dayOfMonth, hour, minute)
    }

}
