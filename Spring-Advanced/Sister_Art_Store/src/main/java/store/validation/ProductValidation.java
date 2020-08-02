package store.validation;

import store.model.entity.Product;
import store.model.service.ProductServiceModel;

public interface ProductValidation {
    boolean isValid(Product product);

    boolean isValid(ProductServiceModel product);
}
