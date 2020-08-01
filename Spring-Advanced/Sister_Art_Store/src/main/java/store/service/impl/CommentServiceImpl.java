package store.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import store.model.entity.Article;
import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;
import store.repository.CommentRepository;
import store.service.CommentService;

import javax.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final ModelMapper modelMapper;
    private final Validator validator;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, Validator validator) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.validator = validator;
    }

    @Override
    public CommentServiceModel addComment(CommentServiceModel commentServiceModel, ArticleServiceModel articleServiceModel) {
        if (!validator.validate(commentServiceModel).isEmpty()){
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
