package store.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import store.model.entity.Article;
import store.model.service.ArticleServiceModel;
import store.repository.ArticleRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ArticleServiceTests {

    @Autowired
    ArticleService service;

    @MockBean
    ArticleRepository articleRepository;

    private List<Article> articles;

    @Before
    public void setupTest() {
        articles = new ArrayList<>();
        when(articleRepository.findAll())
                .thenReturn(articles);
    }

    @Test
    public void findAllArticles_whenArticles_returnArticles() {
        Article article = new Article();
        articles.add(article);

        var result = service.findAllArticles();
        assertEquals(1, result.size());
    }
    @Test
    public void getAll_whenThereAreNoNews_shouldReturnEmpty() {
        when(articleRepository.findAll())
                .thenReturn(new ArrayList<>());

        List<ArticleServiceModel> serviceModels = service.findAllArticles();

        assertEquals(0, serviceModels.size());
    }

    @Test
    public void getById_whenIdIsCorrect_shouldReturn() {

        Article article = new Article();
        article.setId("1");

        when(articleRepository.findById("1"))
                .thenReturn(Optional.of(article));

        ArticleServiceModel serviceModel = service.findArticleById("1");

        assertEquals("1", serviceModel.getId());
    }

    @Test
    public void getById_whenIdIsNotCorrect_shouldThrowException() {
        when(articleRepository.findById("1"))
                .thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> service.findArticleById("1"));
    }

}
