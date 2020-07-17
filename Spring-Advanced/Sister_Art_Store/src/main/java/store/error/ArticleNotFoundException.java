package store.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Article not found")
public class ArticleNotFoundException extends BaseException {


    public ArticleNotFoundException() {
        super(HttpStatus.NOT_FOUND.value());
    }

    public ArticleNotFoundException(String message) {
        super(HttpStatus.NOT_FOUND.value(),message);
    }

}
