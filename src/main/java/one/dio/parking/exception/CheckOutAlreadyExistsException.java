package one.dio.parking.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CheckOutAlreadyExistsException extends Exception {

    public CheckOutAlreadyExistsException() {
        super("CheckOut already exists.");
    }

}
