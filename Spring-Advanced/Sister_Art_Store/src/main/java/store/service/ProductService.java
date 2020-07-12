package store.service;


import store.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel createProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

//    ProductServiceModel findProductById(String id);
//
//    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);
//
//    ProductServiceModel decreaseProductQuantity(String productId, int value, ProductServiceModel productServiceModel);
//
//    void deleteProduct(String id);

    List<ProductServiceModel> findAllByCategory(String category);
}
