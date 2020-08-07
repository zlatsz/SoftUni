package store.service;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import store.model.entity.Category;
import store.model.entity.Product;
import store.model.service.ProductServiceModel;
import store.model.service.UserServiceModel;
import store.repository.OrderRepository;
import store.repository.ProductRepository;
import store.repository.UserRepository;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductServiceTests {

    @Autowired
    ProductService productService;

    @MockBean
    ProductRepository mockProductRepository;

    @MockBean
    OrderRepository mockOrderRepository;

    @MockBean
    UserRepository mockUserRepository;
    @Mock
    ModelMapper modelMapper;
    private ProductServiceModel productServiceModel;
    private Product product;
    private List<Product> productList;

    @Before
    public void setup(){

        productServiceModel = new ProductServiceModel(){{
            setId("1");
            setQuantity(2);
            setPrice(BigDecimal.TEN);
            setDescription("Test country");
            setCategories(new ArrayList<>());
            setName("Test product");
            setImageUrl("Test img");
        }};

        product = new Product(){{
            setId(productServiceModel.getId());
            setQuantity(productServiceModel.getQuantity());
            setCategories(new ArrayList<>() {{
                add(new Category());
            }});
            setPrice(productServiceModel.getPrice());
            setDescription(productServiceModel.getDescription());
            setImageUrl(productServiceModel.getImageUrl());
            setName(productServiceModel.getName());
        }};

        this.productList = new ArrayList<>(){{
            add(product); add(product);
        }};

        when(mockProductRepository.findAll())
                .thenReturn(this.productList);

        when(mockProductRepository.findById(productServiceModel.getId()))
                .thenReturn(Optional.ofNullable(product));
    }


    @Test
    public void findAllProducts_whenListEmpty_ShouldReturnIsEmpty(){
        this.productList.clear();
        List<ProductServiceModel> products = this.productService.findAllProducts();
        assertTrue(products.isEmpty());
    }

    @Test
    public void findAllProducts_whenListHasProducts_ShouldReturnCorrectSize(){
        List<ProductServiceModel> products = this.productService.findAllProducts();
        assertEquals(2, products.size());
    }

    @Test
    public void findProductByID_whenIDExists_ShouldReturnProduct(){
        ProductServiceModel model = this.productService.findProductById(productServiceModel.getId());
        assertEquals(model.getId(), productServiceModel.getId());
        assertEquals(model.getName(), productServiceModel.getName());
        assertEquals(model.getPrice(), productServiceModel.getPrice());
    }

}
