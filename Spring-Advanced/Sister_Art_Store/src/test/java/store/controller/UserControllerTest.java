package store.controller;


import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.test.web.servlet.MockMvc;
import store.model.entity.User;
import store.repository.UserRepository;
import store.web.controllers.UserController;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    UserController userController;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    @Transactional
    public void login_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/users/login"))
                .andExpect(view().name("users/login"));
    }

    @Test
    @Transactional
    public void register_ReturnsCorrectView() throws Exception {
        this.mvc
                .perform(get("/users/register"))
                .andExpect(view().name("users/register"));
    }

    @Test
    @Transactional
    public void register_failRegistersUserCorrectly_redirectCorrectly() throws Exception {
        this.mvc
                .perform(
                        post("/register")
                                .param("username", "pesho")
                                .param("password", "1234")
                                .param("confirmPassword", "1234")
                                .param("email", "1234@abv.bg")
                );
        Assert.assertEquals(0, this.userRepository.count());
    }



}