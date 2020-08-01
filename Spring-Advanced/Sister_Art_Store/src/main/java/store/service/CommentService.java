package store.service;

import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;

import java.util.List;
import java.util.Optional;

public interface CommentService {

    CommentServiceModel addComment(CommentServiceModel commentServiceModel, ArticleServiceModel article);

    List<CommentServiceModel> findAllComments();

    void deleteComment(String deleteId);

    Optional<Comment> findCommentById(String id);


}
