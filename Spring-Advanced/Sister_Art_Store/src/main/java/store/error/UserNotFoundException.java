package store.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "Username not found!")
public class UserNotFoundException extends BaseException{

    public UserNotFoundException(String message) {
        super(HttpStatus.BAD_REQUEST.value(), message);
    }
}
