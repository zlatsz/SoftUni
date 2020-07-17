package store.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "Product name exists.")
public class ProductNameAlreadyExistsException extends BaseException {

    public ProductNameAlreadyExistsException() {
        super(HttpStatus.CONFLICT.value());
    }

    public ProductNameAlreadyExistsException(String message) {
        super(HttpStatus.CONFLICT.value(), message);
    }


}
