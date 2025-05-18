package it.fulminazzo.userstalker.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents the times a user has logged in given a certain period.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginCount {

    private String username;

    private int loginCount;

}
