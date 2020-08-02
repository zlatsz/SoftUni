package store.validation;

import org.springframework.stereotype.Component;
import store.model.entity.Category;
import store.model.service.CategoryServiceModel;

@Component
public class CategoryValidationImpl implements CategoryValidation {
    @Override
    public boolean isValid(Category category) {
        return category!=null;
    }

    @Override
    public boolean isValid(CategoryServiceModel categoryServiceModel) {

        return categoryServiceModel!=null;
    }
}
