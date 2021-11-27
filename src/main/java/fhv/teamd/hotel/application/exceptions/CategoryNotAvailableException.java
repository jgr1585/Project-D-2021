package fhv.teamd.hotel.application.exceptions;

public class CategoryNotAvailableException extends Exception {

    public CategoryNotAvailableException(String message) {
        super(message);
    }

    public CategoryNotAvailableException(String message, Throwable cause) {
        super(message, cause);
    }

}
