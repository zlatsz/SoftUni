package store.validation;

import store.model.entity.Article;
import store.model.entity.Category;
import store.model.service.ArticleServiceModel;
import store.model.service.CategoryServiceModel;

public interface CategoryValidation {
    boolean isValid(Category category);

    boolean isValid(CategoryServiceModel categoryServiceModel);
}
