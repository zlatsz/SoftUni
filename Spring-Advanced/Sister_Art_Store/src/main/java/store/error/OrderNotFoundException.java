package store.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;



@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Order not found.")
public class OrderNotFoundException extends BaseException {

    public OrderNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }

    public OrderNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(),message);
    }

}
