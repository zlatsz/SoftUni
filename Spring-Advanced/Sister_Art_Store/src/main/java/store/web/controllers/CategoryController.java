package store.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import store.model.binding.CategoryAddBindingModel;
import store.model.service.CategoryServiceModel;
import store.model.service.ProductServiceModel;
import store.model.view.CategoryAllViewModel;
import store.service.CategoryService;
import store.service.ProductService;
import store.validation.CategoryAddValidator;
import store.validation.CategoryEditValidator;
import store.web.annotations.PageTitle;

import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/categories")
public class CategoryController extends BaseController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryAddValidator categoryAddValidator;
    private final CategoryEditValidator categoryEditValidator;


    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper, CategoryAddValidator categoryAddValidator, CategoryEditValidator categoryEditValidator,ProductService productService) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.categoryAddValidator = categoryAddValidator;
        this.categoryEditValidator = categoryEditValidator;
        this.productService = productService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Add Category")
    public ModelAndView addCategory(ModelAndView modelAndView, @ModelAttribute(name = "model")
            CategoryAddBindingModel categoryAddBindingModel) {
        modelAndView.addObject("model", categoryAddBindingModel);
        return view("categories/add-cat");
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addCategoryConfirm(ModelAndView modelAndView, @ModelAttribute(name = "model") CategoryAddBindingModel categoryAddBindingModel, BindingResult bindingResult) {

        this.categoryAddValidator.validate(categoryAddBindingModel, bindingResult);

        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", categoryAddBindingModel);
            return view("categories/add-cat", modelAndView);
        }

        CategoryServiceModel categoryServiceModel = this.modelMapper.map(categoryAddBindingModel, CategoryServiceModel.class);
        this.categoryService.addCategory(categoryServiceModel);
        return redirect("/categories/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Categories")
    public ModelAndView allCategories(ModelAndView modelAndView) {
        List<CategoryServiceModel> categoryServiceModels = this.categoryService.getAll();
        List<CategoryAllViewModel> categories = categoryServiceModels.stream()
                .map(c -> this.modelMapper.map(c, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        return view("categories/all-cat", modelAndView);
    }


//    @PostMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ModelAndView deleteCategoryConfirm(@PathVariable String id) {
//        this.categoryService.deleteCategory(id);
//
//        return redirect("/categories/all");
//    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView delete(@ModelAttribute(name="deleteId") String deleteId, ModelAndView modelAndView) {
        CategoryServiceModel category = this.categoryService.findCategoryById(deleteId);
        List<ProductServiceModel> allByCategory = this.productService.findAllByCategory(category.getName());
        if (!allByCategory.isEmpty()){
            modelAndView.addObject("notFound", true);
            return view("categories/all-cat", modelAndView);
        } else {
            this.categoryService.deleteCategory(deleteId);
            return redirect("/categories/all");
        }
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit Category")
    public ModelAndView editCategory(@PathVariable String id, ModelAndView modelAndView) {

        modelAndView.addObject("model",
                this.modelMapper.map(this.categoryService.findCategoryById(id), CategoryAllViewModel.class));

        return view("categories/edit-cat", modelAndView);
    }

    @PostMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editCategoryConfirm(ModelAndView modelAndView,
                                            @PathVariable String id,
                                            @ModelAttribute(name = "model") CategoryAddBindingModel model, BindingResult bindingResult) {

        this.categoryEditValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("categoryId", id);
            modelAndView.addObject("model", model);
            return view("categories/edit-cat", modelAndView);
        }
        this.categoryService.editCategory(id, this.modelMapper.map(model, CategoryServiceModel.class));

        return redirect("/categories/all");
    }

    @GetMapping("/fetch")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseBody
    public List<CategoryAllViewModel> fetchByCategory() {

        return this.categoryService.findAllCategories()
                .stream()
                .map(c -> this.modelMapper.map(c, CategoryAllViewModel.class))
                .collect(Collectors.toList());
    }


}
