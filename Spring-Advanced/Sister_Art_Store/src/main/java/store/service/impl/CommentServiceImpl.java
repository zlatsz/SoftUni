package store.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import store.model.entity.Article;
import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;
import store.repository.CommentRepository;
import store.service.CommentService;
import store.validation.CommentValidation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private CommentValidation commentValidation;


    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, CommentValidation commentValidation) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.commentValidation = commentValidation;
    }

    @Override
    public CommentServiceModel addComment(CommentServiceModel commentServiceModel, ArticleServiceModel articleServiceModel) {
        if (!commentValidation.isValid(commentServiceModel)){
            throw new IllegalArgumentException("Comment not found");
        }
        Comment comment = this.modelMapper.map(commentServiceModel, Comment.class);
        Article article = this.modelMapper.map(articleServiceModel, Article.class);
        article.getComments().add(comment);

        return this.modelMapper
                .map(this.commentRepository.saveAndFlush(comment), CommentServiceModel.class);
    }

    @Override
    public List<CommentServiceModel> findAllComments() {
        return this.commentRepository.findAll()
                .stream()
                .map(c -> this.modelMapper.map(c, CommentServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteComment(String deleteId) {
        this.commentRepository.deleteById(deleteId);
    }

    @Override
    public Optional<Comment> findCommentById(String id) {
        return this.commentRepository.findById(id);
    }


}
