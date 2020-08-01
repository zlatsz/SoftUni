package store.service.impl;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import store.error.ProductNotFoundException;
import store.model.entity.Category;
import store.model.entity.Product;
import store.model.service.ProductServiceModel;
import store.repository.ProductRepository;
import store.service.ProductService;

import javax.validation.Validator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    //    private final OfferRepository offerRepository;
//    private final ProductValidationService productValidation;
    private final ModelMapper modelMapper;
    private final Validator validator;


    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelMapper, Validator validator) {
        this.productRepository = productRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public ProductServiceModel createProduct(ProductServiceModel productServiceModel) {
        if (!validator.validate(productServiceModel).isEmpty()) {
            throw new ProductNotFoundException("Product not found.");
        }
        Product product = this.modelMapper.map(productServiceModel, Product.class);
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

//        this.offerRepository.findByProduct_Id(product.getId())
//                .ifPresent((o) -> {
//                    o.setPrice(product.getPrice().multiply(new BigDecimal(GlobalConstants.DISCOUNT_RATIO)));
//                    this.offerRepository.save(o);
//                });

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
//       return this.productRepository.findAllByCategories(category)
//       .stream()
//       .map(product -> this.modelMapper.map(product, ProductServiceModel.class))
//                .collect(Collectors.toList());

    }
}
