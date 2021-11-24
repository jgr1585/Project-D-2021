package fhv.teamd.hotel.application.exceptions;

public class OccupiedRoomException extends Exception {

    public OccupiedRoomException(String message) {
        super(message);
    }

    public OccupiedRoomException(String message, Throwable cause) {
        super(message, cause);
    }

}
