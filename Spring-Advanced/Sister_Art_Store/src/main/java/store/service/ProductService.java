package store.service;


import store.model.entity.IndexProduct;
import store.model.entity.Product;
import store.model.service.ProductServiceModel;

import java.util.List;

public interface ProductService {

    ProductServiceModel createProduct(ProductServiceModel productServiceModel);

    List<ProductServiceModel> findAllProducts();

    ProductServiceModel findProductById(String id);

    ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel);

    ProductServiceModel deleteProduct(String id);

    List<ProductServiceModel> findAllByCategory(String category);

    List<Product> listAll(String keyword);

    ProductServiceModel decreaseProductQuantity(String productId, int quantity, ProductServiceModel productServiceModel);

    List<IndexProduct> indexView();
}
