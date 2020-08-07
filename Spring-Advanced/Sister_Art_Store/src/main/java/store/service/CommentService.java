package store.service;

import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;

import java.util.List;

public interface CommentService {

    CommentServiceModel addComment(CommentServiceModel commentServiceModel, ArticleServiceModel article);

    List<CommentServiceModel> findAllComments();

    void deleteComment(String deleteId);

    CommentServiceModel findCommentById(String id);

    void cleanUpOldComment();
}
