package store.validation;


import org.springframework.stereotype.Component;
import store.model.entity.Product;
import store.model.service.CategoryServiceModel;
import store.model.service.ProductServiceModel;

import java.util.List;

@Component
public class ProductionValidationImpl implements ProductValidation {
    @Override
    public boolean isValid(Product product) {
        return product != null;
    }

    @Override
    public boolean isValid(ProductServiceModel product) {
        return product != null
                && areCategoriesValid(product.getCategories());
    }

    private boolean areCategoriesValid(List<CategoryServiceModel> categories) {
        return categories != null && !categories.isEmpty();
    }
}
