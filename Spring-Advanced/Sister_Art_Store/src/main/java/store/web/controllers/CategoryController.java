package store.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import store.model.binding.CategoryAddBindingModel;
import store.model.service.CategoryServiceModel;
import store.service.CategoryService;
import store.validation.CategoryAddValidator;
import store.web.annotations.PageTitle;


@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final CategoryAddValidator categoryAddValidator;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper, CategoryAddValidator categoryAddValidator) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.categoryAddValidator = categoryAddValidator;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Category")
    public ModelAndView addCategory(ModelAndView modelAndView, @ModelAttribute(name = "model") CategoryAddBindingModel categoryAddBindingModel) {
        modelAndView.addObject("model", categoryAddBindingModel);
        return super.view("categories/add-category");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addCategoryConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") CategoryAddBindingModel categoryAddBindingModel, BindingResult bindingResult) {
        this.categoryAddValidator.validate(categoryAddBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", categoryAddBindingModel);
            return super.view("category/add-category", modelAndView);
        }

        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class);
        this.categoryService.addCategory(categoryServiceModel);
        return super.redirect("/home");
    }

//    @GetMapping("/all")
//    @PreAuthorize("isAuthenticated()")
//    @PageTitle("Add Categories")
//    public ModelAndView allCategories(ModelAndView modelAndView) {
//        List<CategoryServiceModel> categoryServiceModels = this.categoryService.getAll();
//        List<CategoryAllViewModel> categories = categoryServiceModels.stream().map(c -> this.modelMapper.map(c, CategoryAllViewModel.class)).collect(Collectors.toList());
//        modelAndView.addObject("categories", categories);
//        return super.view("category/all-categories", modelAndView);
//    }
//
//    @GetMapping("/delete/{id}")
//    @PreAuthorize("isAuthenticated()")
//    @PageTitle("Delete Category")
//    public ModelAndView deleteCateogory(@PathVariable String id, ModelAndView modelAndView){
//        CategoryServiceModel categoryServiceModel = this.categoryService.getById(id);
//        AddCategoryModel category = this.modelMapper.map(categoryServiceModel, AddCategoryModel.class);
//        category.setName(categoryServiceModel.getName());
//
//        modelAndView.addObject("category", category);
//        modelAndView.addObject("categoryId", id);
//
//        return super.view("category/delete-category", modelAndView);
//
//    }
//
//    @PostMapping("/delete/{id}")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView deleteProductConfirm(@PathVariable String id) {
//        this.categoryService.deleteCategory(id);
//        return super.redirect("/home");
//    }
//
//
//    // LOAD ASYNC CATEGORIES
//    @GetMapping("/fetch")
//    @ResponseBody
//    public List<CategoryServiceModel> fetchCategories() {
//
//        // MAY NEED CATEGORYVIEWMODEL
//        return this.categoryService.getAll();
//    }

}
