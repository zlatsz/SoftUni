package store.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import store.error.ProductAlreadyExistsException;
import store.error.ProductNotFoundException;
import store.model.entity.Category;
import store.model.entity.Product;
import store.model.service.ProductServiceModel;
import store.model.view.ProductViewIndexModel;
import store.repository.ProductRepository;
import store.service.CategoryService;
import store.service.ProductService;
import store.validation.ProductValidation;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ProductValidation productValidation;
    private final ModelMapper modelMapper;


    private final List<ProductViewIndexModel> offers = new ArrayList<>();

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryService categoryService, ProductValidation productValidation,
                              ModelMapper modelMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productValidation = productValidation;
        this.modelMapper = modelMapper;

    }

    @Override
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel) {
        if (!productValidation.isValid(productServiceModel)) {
            throw new ProductNotFoundException("Product not found.");
        }
        Product product = this.productRepository
                .findByName(productServiceModel.getName())
                .orElse(null);

        if (product != null) {
            throw new ProductAlreadyExistsException("Product with this name already exists");
        }
        product = this.modelMapper.map(productServiceModel, Product.class);

        List<Category> categories = productServiceModel.getCategories().stream()
                .map(c -> this.modelMapper.map(c, Category.class))
                .collect(Collectors.toList());
        product.setCategories(categories);

        return this.modelMapper.map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllProducts() {
        return this.productRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> listAll(String keyword) {
        if (keyword != null) {
            return productRepository.search(keyword);
        }
        return productRepository.findAll();
    }

    @Override
    public ProductServiceModel findProductById(String id) {
        return this.productRepository.findById(id)
                .map(p -> this.modelMapper
                        .map(p, ProductServiceModel.class))
                .orElseThrow(ProductNotFoundException::new);
    }

    @Override
    public ProductServiceModel editProduct(String id, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(id)
                .orElseThrow(ProductNotFoundException::new);

        product.setName(productServiceModel.getName());
        product.setDescription(productServiceModel.getDescription());
        product.setPrice(productServiceModel.getPrice());
        product.setQuantity(productServiceModel.getQuantity());
        product.setCategories(
                productServiceModel.getCategories()
                        .stream()
                        .map(c -> this.modelMapper.map(c, Category.class))
                        .collect(Collectors.toList())
        );

        return this.modelMapper
                .map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }


    @Override
    public ProductServiceModel deleteProduct(String id) {
        Product product = this.productRepository
                .findById(id)
                .orElseThrow(ProductNotFoundException::new);
        this.productRepository.delete(product);
        return this.modelMapper.map(product, ProductServiceModel.class);
    }

    @Override
    public List<ProductServiceModel> findAllByCategory(String category) {
//        List<ProductServiceModel> list = new ArrayList<>();
//        for (Product product : this.productRepository.findAll()) {
//            List<Category> categories = product.getCategories();
//            for (Category c : categories) {
//                List<CategoryName> categoryNames = c.getCategoryNames();
//                for (CategoryName categoryName : categoryNames) {
//                    if(categoryName.getName().equals(category)){
//                        ProductServiceModel map = this.modelMapper.map(product, ProductServiceModel.class);
//                        list.add(map);
//                    }
//                }
//            }
//        }
//        return list;
        return this.productRepository.findAll()
                .stream()
                .filter(product -> product.getCategories().stream().anyMatch(categoryStream -> categoryStream.getName().equals(category)))
                .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
                .collect(Collectors.toList());

    }

    @Override
    public ProductServiceModel decreaseProductQuantity(String productId, int value, ProductServiceModel productServiceModel) {
        Product product = this.productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Product not found"));
        product.setQuantity(product.getQuantity() - value);
        return this.modelMapper
                .map(this.productRepository.saveAndFlush(product), ProductServiceModel.class);
    }

//    @Scheduled(fixedRate = 50000)
//    @Scheduled(initialDelay = 0, fixedRate = 3600000)
//    public void scheduleProducts() {
//        offers.clear();
//        System.out.println("Change offers");
//        this.indexView();
//    }
//
//    @Override
//    public List<ProductViewIndexModel> indexView() {
//        List<ProductServiceModel> products = this.findAllProducts();
//        if (products.size()<4) {
//            return null;
//        }
//        Random rnd = new Random();
//        for (int i = 0; i < 4; i++) {
//            ProductViewIndexModel product = this.modelMapper.map(products.get(rnd.nextInt(products.size())), ProductViewIndexModel.class);
//            if(offers.stream().anyMatch(o->o.getId().equals(product.getId()))) {
//                i--;
//            } else {
//                offers.add(product);
//            }
//        }
//        return offers;
//    }
}
