package store.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND,  reason = "Category not found")
public class CategoryNotFoundException extends RuntimeException  {

    private int statusCode;

    public CategoryNotFoundException() {


    }

    public CategoryNotFoundException(String message) {
        super(message);
        this.statusCode = HttpStatus.NOT_FOUND.value();
    }

    public int getStatusCode() {
        return statusCode;
    }
}
