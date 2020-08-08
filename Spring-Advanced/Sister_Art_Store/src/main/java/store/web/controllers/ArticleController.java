package store.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import store.model.binding.ArticleAddBindingModel;
import store.model.binding.CommentAddBindingModel;
import store.model.binding.SearchBindingModel;
import store.model.entity.Article;
import store.model.service.*;
import store.model.view.*;
import store.service.*;
import store.validation.ArticleAddValidator;
import store.validation.ArticleEditValidator;
import store.web.annotations.PageTitle;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticleController extends BaseController {

    private final ArticleService articleService;
    private final UserService userService;
    private final CategoryService categoryService;
    private final CommentService commentService;
    private final ModelMapper modelMapper;
    private final ArticleAddValidator addValidator;
    private final ArticleEditValidator editValidator;
    private final CloudinaryService cloudinaryService;


    public ArticleController(ArticleService articleService, UserService userService, CategoryService categoryService, CommentService commentService, ModelMapper modelMapper, ArticleAddValidator addValidator, ArticleEditValidator editValidator, CloudinaryService cloudinaryService) {
        this.articleService = articleService;
        this.userService = userService;
        this.categoryService = categoryService;
        this.commentService = commentService;
        this.modelMapper = modelMapper;
        this.addValidator = addValidator;
        this.editValidator = editValidator;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Add Article")
    public ModelAndView addArticle(ModelAndView modelAndView,
                                   @ModelAttribute(name = "model") ArticleAddBindingModel model,
                                   @ModelAttribute(name = "comment") CommentAddBindingModel comment) {
        modelAndView.addObject("model", model);
        modelAndView.addObject("comment", comment);
        return view("articles/add-article", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addArticleConfirm(Principal principal, ModelAndView modelAndView,
                                          @ModelAttribute(name = "model") ArticleAddBindingModel model,
                                          BindingResult bindingResult) throws IOException {
        this.addValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return view("articles/add-article", modelAndView);
        }
        ArticleServiceModel articleServiceModel = this.modelMapper
                .map(model, ArticleServiceModel.class);
        UserServiceModel user = this.userService.findUserByUserName(principal.getName());
        articleServiceModel.setUploader(user);
        articleServiceModel.setPdfUrl(
                this.cloudinaryService.uploadImage(model.getPdfUrl()));
        this.articleService.addArticle(articleServiceModel);
        return redirect("/articles/all");
    }

    @GetMapping("/info")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("All Articles")
    public ModelAndView allArticlesInfo(ModelAndView modelAndView, Principal principal) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("articles",
                this.articleService.findAllArticles()
                        .stream()
                        .map(a -> this.modelMapper.map(a, ArticleDetailsViewModel.class))
                        .collect(Collectors.toList()));
        SearchBindingModel model = new SearchBindingModel();
        modelAndView.addObject("model", model);
        return view("articles/all-details", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Articles")
    public ModelAndView allArticles(ModelAndView modelAndView, Principal principal) {
        List<ArticleServiceModel> articles = this.articleService.findAllArticles();
        UserServiceModel user = this.userService.findUserByUserName(principal.getName());
        modelAndView.addObject("articles", articles);
        modelAndView.addObject("username", user.getUsername());
        return view("articles/all-articles", modelAndView);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Article Details")
    public ModelAndView detailsArticle(@PathVariable String id, ModelAndView modelAndView) {
        ArticleDetailsViewModel model = this.modelMapper
                .map(this.articleService.findArticleById(id), ArticleDetailsViewModel.class);
        modelAndView.addObject("model", model);
        List<CommentServiceModel> comments = this.commentService.findAllComments().stream()
                .filter(a->a.getArticle().getId().equals(id))
                .sorted(Comparator.comparing(CommentServiceModel::getCommentDate))
                .collect(Collectors.toList());
        modelAndView.addObject("comments", comments);
        modelAndView.addObject("articleId", id);
        return view("articles/details", modelAndView);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ModelAttribute(name="deleteId") String deleteId) {
        this.articleService.deleteArticle(deleteId);
        return "redirect:/articles/all";
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit Article")
    public ModelAndView editProduct(@PathVariable String id,
                                    ModelAndView modelAndView,
                                    @ModelAttribute(name = "model") ArticleAddBindingModel model) {
        ArticleServiceModel articleServiceModel = this.articleService.findArticleById(id);
        model = this.modelMapper.map(articleServiceModel, ArticleAddBindingModel.class);
        modelAndView.addObject("model", model);
        modelAndView.addObject("articleId", id);
        return view("articles/edit-article", modelAndView);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editProductConfirm(@PathVariable String id,
                                           ModelAndView modelAndView,
                                           @ModelAttribute(name = "model") ArticleAddBindingModel model,
                                           BindingResult bindingResult) {
        this.editValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("articleId", id);
            modelAndView.addObject("model", model);
            return view("articles/edit-article", modelAndView);
        }
       ArticleServiceModel articleServiceModel = this.modelMapper.map(model, ArticleServiceModel.class);
        this.articleService.editArticle(id, articleServiceModel);
        return redirect("/articles/details/" + id);
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public List<ArticleAllViewModel> fetchByCategory() {

        return this.articleService.findAllArticles()
                .stream()
                .map(a -> this.modelMapper.map(a, ArticleAllViewModel.class))
                .collect(Collectors.toList());
    }
}
