package it.fulminazzo.userstalker.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * A general exception that holds a {@link HttpStatus} with it.
 * To be returned to the requesting client.
 */
@Getter
public final class HttpRequestException extends RuntimeException {
    private final HttpStatus status;

    /**
     * Instantiates a new Http request exception.
     *
     * @param status  the http status of the error
     * @param message the error message
     * @param args    the arguments to format the error message with
     */
    public HttpRequestException(final HttpStatus status, final String message, final Object... args) {
        super(String.format(message, args));
        this.status = status;
    }

    /**
     * Returns a {@link HttpRequestException} with a message of invalid size.
     *
     * @return the http request exception
     */
    public static HttpRequestException invalidSizeGreaterThan0() {
        return new HttpRequestException(HttpStatus.BAD_REQUEST, "Invalid size specified: required a number greater than 0");
    }

}
