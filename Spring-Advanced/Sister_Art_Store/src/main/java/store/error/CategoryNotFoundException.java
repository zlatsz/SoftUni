package store.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,  reason = "Category not found")
public class CategoryNotFoundException extends BaseException {


    public CategoryNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }

    public CategoryNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(),message);
    }

}
