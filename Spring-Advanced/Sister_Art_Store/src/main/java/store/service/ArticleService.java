package store.service;

import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;

import java.util.List;

public interface ArticleService {

    ArticleServiceModel addArticle (ArticleServiceModel articleServiceModel);

    List<ArticleServiceModel> findAllArticles();

    ArticleServiceModel findArticleById(String id);

    ArticleServiceModel editArticle(String id, ArticleServiceModel articleServiceModel);

    ArticleServiceModel deleteArticle(String id);


}
