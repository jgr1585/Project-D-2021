package fhv.teamd.hotel.application.exceptions;

public class InvalidIdException extends Exception {
    public InvalidIdException(String message) {
        super(message);
    }

    public InvalidIdException(String message, Throwable cause) {
        super(message, cause);
    }
}
