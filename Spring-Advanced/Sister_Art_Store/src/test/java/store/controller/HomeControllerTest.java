package store.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class HomeControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void index_ReturnsCorrectView() throws Exception {
        this.mvc.perform(get("/")).andExpect(view().name("index"));
    }

    @Test
    @WithMockUser
    public void home_ReturnsCorrectView() throws Exception {
        this.mvc.perform(get("/home")).andExpect(view().name("home"));
    }

    @Test
    public void oil_ReturnsCorrectView() throws Exception {
        this.mvc.perform(get("/aboutOil")).andExpect(view().name("about-oil"));
    }

    @Test
    public void sister_ReturnsCorrectView() throws Exception {
        this.mvc.perform(get("/aboutSisters")).andExpect(view().name("about-us"));
    }

    @Test
    public void contact_ReturnsCorrectView() throws Exception {
        this.mvc.perform(get("/contacts")).andExpect(view().name("contacts"));
    }

}

