package store.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import store.model.entity.Article;
import store.model.entity.Product;
import store.repository.ProductRepository;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import store.web.controllers.ProductController;
import store.web.controllers.UserController;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters=false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private ProductRepository mockProductRepository;

    @Autowired
    ProductController productController;

    private ArrayList<Product> products;


    @Before
    public void setupTest(){
        products = new ArrayList<>();

        when(mockProductRepository.findAllByCategories(any()))
                .thenReturn(products);
    }

    @Test
    @Transactional
    @WithMockUser
    public void returnsCorrectView_jewellery() throws Exception {
        this.mvc
                .perform(get("/products/jewellery"))
                .andExpect(view().name("products/all-jewellery"));
    }

    @Test
    @Transactional
    @WithMockUser
    public void returnsCorrectView_oil() throws Exception {
        this.mvc
                .perform(get("/products/oil"))
                .andExpect(view().name("products/all-oil"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles={"USER", "ADMIN"})
    public void list_returnsCorrectAttribute() throws Exception {
        this.mvc.perform(get("/products/all"))
                .andExpect(view().name("products/all-products"))
                .andExpect(model().attributeExists("products"));
    }

    @Test
    @Transactional
    @WithMockUser(username = "admin", roles={"USER", "ADMIN"})
    public void all_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/products/all"))
                .andExpect(view().name("products/all-products"));
    }


    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void add_returnsCorrectView() throws Exception {
        this.mvc.perform(get("/products/add"))
                .andExpect(view().name("products/add-product"));
    }


    @Test
    @WithMockUser(value = "admin", roles = {"ADMIN"})
    public void delete_deletesEntityCorrectly() throws Exception {
        long result = mockProductRepository.count();
        this.mvc.perform(post("/products/delete/"));
        Assert.assertEquals(result,mockProductRepository.count());
    }
}
