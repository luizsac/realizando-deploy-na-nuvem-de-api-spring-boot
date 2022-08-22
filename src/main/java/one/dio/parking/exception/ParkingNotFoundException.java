package one.dio.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.NoSuchElementException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ParkingNotFoundException extends NoSuchElementException {

    public ParkingNotFoundException(String id) {
        super("Parking with id = " + id + " not found");
    }

}
