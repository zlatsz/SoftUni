package store.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import store.error.ArticleNotFoundException;
import store.error.CategoryNotFoundException;
import store.model.entity.Article;
import store.model.entity.Category;
import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CategoryServiceModel;
import store.model.service.CommentServiceModel;
import store.repository.ArticleRepository;
import store.service.ArticleService;

import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public ArticleServiceImpl(ArticleRepository articleRepository, ModelMapper modelMapper, Validator validator) {
        this.articleRepository = articleRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }


    @Override
    public ArticleServiceModel addArticle(ArticleServiceModel articleServiceModel) {
        if (!validator.validate(articleServiceModel).isEmpty()){
            throw new ArticleNotFoundException("Article not found");
        }
        Article article = this.modelMapper.map(articleServiceModel, Article.class);
        article.setPostTime(LocalDateTime.now());
        return this.modelMapper
                .map(this.articleRepository.saveAndFlush(article), ArticleServiceModel.class);
    }

    @Override
    public List<ArticleServiceModel> findAllArticles() {
        return this.articleRepository.findAll()
                .stream()
                .map(a -> this.modelMapper.map(a, ArticleServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public ArticleServiceModel findArticleById(String id) {
        Article article =  this.articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        return this.modelMapper.map(article, ArticleServiceModel.class);
    }

    @Override
    public ArticleServiceModel editArticle(String id, ArticleServiceModel articleServiceModel) {
        Article article =  this.articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        article.setName(articleServiceModel.getName());
        article.setPostTime(LocalDateTime.now());
        return this.modelMapper
                .map(this.articleRepository.saveAndFlush(article), ArticleServiceModel.class);
    }

    @Override
    public ArticleServiceModel deleteArticle(String id) {
        Article article =  this.articleRepository.findById(id)
                .orElseThrow(ArticleNotFoundException::new);
        this.articleRepository.delete(article);
        return this.modelMapper.map(article, ArticleServiceModel.class);
    }

}
