package store.validation;

import store.model.entity.Comment;
import store.model.entity.Product;
import store.model.service.CommentServiceModel;
import store.model.service.ProductServiceModel;

public interface CommentValidation {
    boolean isValid(Comment comment);

    boolean isValid(CommentServiceModel comment);
}
