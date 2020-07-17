package store.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Product not found.")
public class ProductNotFoundException extends BaseException {


    public ProductNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }

    public ProductNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(),message);
    }


}
