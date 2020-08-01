package store.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import store.model.binding.CommentAddBindingModel;
import store.model.binding.ProductAddBindingModel;
import store.model.entity.Article;
import store.model.entity.Comment;
import store.model.service.ArticleServiceModel;
import store.model.service.CommentServiceModel;
import store.model.service.UserServiceModel;
import store.service.ArticleService;
import store.service.CommentService;
import store.service.UserService;
import store.web.annotations.PageTitle;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/comments")
public class CommentController extends BaseController {

    private final CommentService commentService;
    private final UserService userService;
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, UserService userService, ArticleService articleService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.userService = userService;
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/add/{id}")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView createComment(@PathVariable String id, @ModelAttribute CommentAddBindingModel com,
                                      BindingResult bindingResult, Principal principal) {
        CommentServiceModel commentServiceModel = this.modelMapper
                .map(com, CommentServiceModel .class);
        UserServiceModel user = this.userService.findUserByUserName(principal.getName());
        commentServiceModel.setUser(user);
        commentServiceModel.setCommentDate(LocalDateTime.now());
        ArticleServiceModel article = this.articleService.findArticleById(id);
        commentServiceModel.setArticle(article);
        this.commentService.addComment(commentServiceModel, article);
        return redirect("/articles/details/" + id);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@ModelAttribute(name="deleteId") String deleteId) {
        Comment comment = this.commentService.findCommentById(deleteId).orElse(null);
        Article article = comment.getArticle();
        this.commentService.deleteComment(deleteId);
        return redirect("/articles/details/" + article.getId());
    }

}
