package store.controller;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.ModelAndView;
import store.model.entity.Article;
import store.model.entity.Order;
import store.model.service.ArticleServiceModel;
import store.model.view.ArticleDetailsViewModel;
import store.model.view.OrderViewModel;
import store.repository.ArticleRepository;
import store.repository.OrderRepository;
import store.web.controllers.ArticleController;
import store.web.controllers.OrdersController;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc(addFilters=false)
public class ArticleControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Mock
    Principal principal;

    @Autowired
    ArticleController controller;

    @MockBean
    ArticleRepository mockArticleRepository;
    private ArrayList<Article> articles;


    @Before
    public void setupTest(){
        articles = new ArrayList<>();

        when(mockArticleRepository.findAllByUploader_UsernameOrderByPostTime(any()))
                .thenReturn(articles);
    }

    @Test
    @WithMockUser
    public void getNewArticle() {
        articles.clear();
        ModelAndView modelAndView = new ModelAndView();
        when(principal.getName())
                .thenReturn("");

        ModelAndView result = controller.allArticlesInfo(modelAndView, principal);

        List<ArticleDetailsViewModel> viewModels = (List<ArticleDetailsViewModel>) result.getModel().get("articles");
        assertTrue(viewModels.isEmpty());
    }

}