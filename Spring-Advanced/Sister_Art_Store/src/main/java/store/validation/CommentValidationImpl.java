package store.validation;

import org.springframework.stereotype.Component;
import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;

@Component
public class CommentValidationImpl implements CommentValidation {
    @Override
    public boolean isValid(Comment comment) {
       return comment != null;
    }

    @Override
    public boolean isValid(CommentServiceModel comment) {
        return comment != null
                && areArticlesValid(comment.getArticle());
    }

    private boolean areArticlesValid(ArticleServiceModel article) {
        return article != null;
    }
}
