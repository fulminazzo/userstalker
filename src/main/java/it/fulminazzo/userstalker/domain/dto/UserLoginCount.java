package it.fulminazzo.userstalker.domain.dto;

/**
 * Represents the times a user has logged in given a certain period.
 */
public record UserLoginCount(String username, long loginCount) {

}
