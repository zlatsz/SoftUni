package store.validation;

import store.model.entity.Article;
import store.model.service.ArticleServiceModel;

public interface ArticleValidation {

    boolean isValid(Article article);

    boolean isValid(ArticleServiceModel articleServiceModel);
}
