package store.web.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import store.model.binding.ProductAddBindingModel;
import store.model.binding.SearchBindingModel;
import store.model.service.CategoryServiceModel;
import store.model.service.ProductServiceModel;
import store.model.view.CategoryAllViewModel;
import store.model.view.ProductAllViewModel;
import store.model.view.ProductDetailsViewModel;
import store.service.CategoryService;
import store.service.CloudinaryService;
import store.service.ProductService;
import store.validation.ProductAddValidator;
import store.validation.ProductEditValidator;
import store.web.annotations.PageTitle;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final ModelMapper modelMapper;
    private final ProductAddValidator productAddValidator;
    private final ProductEditValidator productEditValidator;
    private final CloudinaryService cloudinaryService;

    public ProductController(ProductService productService, CategoryService categoryService, ModelMapper modelMapper, ProductAddValidator productAddValidator, ProductEditValidator productEditValidator, CloudinaryService cloudinaryService) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
        this.productAddValidator = productAddValidator;
        this.productEditValidator = productEditValidator;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Add Product")
    public ModelAndView addProduct(ModelAndView modelAndView,
                                   @ModelAttribute(name = "model") ProductAddBindingModel model) {
        modelAndView.addObject("model", model);
        return view("products/add-product", modelAndView);
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView addProductConfirm(ModelAndView modelAndView,
                                          @ModelAttribute(name = "model") ProductAddBindingModel model,
                                          BindingResult bindingResult) throws IOException {
        this.productAddValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("model", model);
            return view("products/add-product", modelAndView);
        }
        ProductServiceModel productServiceModel = this.modelMapper
                .map(model, ProductServiceModel.class);
        productServiceModel.setCategories(
                this.categoryService.findAllCategories()
                        .stream()
                        .filter(c -> model.getCategories().contains(c.getId()))
                        .collect(Collectors.toList()));
        productServiceModel.setImageUrl(
                this.cloudinaryService.uploadImage(model.getImage()));
        this.productService.createProduct(productServiceModel);
        return redirect("/products/all");
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Products")
    public ModelAndView allProducts(ModelAndView modelAndView) {
        modelAndView.addObject("products",
                this.productService.findAllProducts()
                        .stream()
                        .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                        .collect(Collectors.toList()));
        return view("products/all-products", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("Edit Product")
    public ModelAndView editProduct(@PathVariable String id,
                                    ModelAndView modelAndView,
                                    @ModelAttribute(name = "model") ProductAddBindingModel model) {
        ProductServiceModel productServiceModel = this.productService.findProductById(id);
        model = this.modelMapper.map(productServiceModel, ProductAddBindingModel.class);
        model.setCategories(productServiceModel.getCategories()
                .stream()
                .map(CategoryServiceModel::getName)
                .collect(Collectors.toList()));
        modelAndView.addObject("model", model);
        modelAndView.addObject("productId", id);
        return view("products/edit-product", modelAndView);
    }

    @PatchMapping("/edit/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ModelAndView editProductConfirm(@PathVariable String id,
                                           ModelAndView modelAndView,
                                           @ModelAttribute(name = "model") ProductAddBindingModel model,
                                           BindingResult bindingResult) {
        this.productEditValidator.validate(model, bindingResult);
        if (bindingResult.hasErrors()) {
            model.setCategories(model
                    .getCategories()
                    .stream()
                    .map(c -> this.categoryService.findCategoryById(c).getName())
                    .collect(Collectors.toList()));
            modelAndView.addObject("productId", id);
            modelAndView.addObject("model", model);
            return view("products/edit-product", modelAndView);
        }
        ProductServiceModel productServiceModel = this.modelMapper.map(model, ProductServiceModel.class);
        productServiceModel.setCategories(model.getCategories()
                .stream()
                .map(c -> this.modelMapper
                        .map(this.categoryService.findCategoryById(c),
                                CategoryServiceModel.class))
                .collect(Collectors.toList()));
        this.productService.editProduct(id, productServiceModel);
        return redirect("/products/details/" + id);
    }

    @GetMapping("/details/{id}")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Product Details")
    public ModelAndView detailsProduct(@PathVariable String id, ModelAndView modelAndView) {
        ProductDetailsViewModel model = this.modelMapper
                .map(this.productService.findProductById(id), ProductDetailsViewModel.class);
        modelAndView.addObject("product", model);
        return view("products/details", modelAndView);
    }
//
//    @PostMapping("/delete/{id}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public ModelAndView deleteProductConfirm(@PathVariable String id) {
//        this.productService.deleteProduct(id);
//        return redirect("/products/all");
//    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ModelAttribute(name="deleteId") String deleteId) {
        this.productService.deleteProduct(deleteId);
        return "redirect:/products/all";
    }

    @GetMapping("/jewellery")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Jewellery")
    public ModelAndView Jewellery(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        List<ProductAllViewModel> jewellery = new ArrayList<>();
        List<ProductServiceModel> products = new ArrayList<>(this.productService.findAllProducts());
        for (ProductServiceModel product : products) {
            if (product.getCategories().stream().anyMatch(p -> p.getName().equals("Jewelleries"))){
                jewellery.add(this.modelMapper.map(product, ProductAllViewModel.class));
            }
        }
        modelAndView.addObject("jewellery", jewellery);
        return view("products/all-jewellery", modelAndView);
    }

    @GetMapping("/oil")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PageTitle("Oil Products")
    public ModelAndView Oil(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        List<ProductAllViewModel> oil = new ArrayList<>();
        List<ProductServiceModel> products = new ArrayList<>(this.productService.findAllProducts());
        for (ProductServiceModel product : products) {
            if (product.getCategories().stream().anyMatch(p -> p.getName().equals("Oil"))){
                oil.add(this.modelMapper.map(product, ProductAllViewModel.class));
            }
        }
        modelAndView.addObject("oil",oil);
        return view("products/all-oil", modelAndView);
    }

    @PostMapping("/search")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView searchConfirm(@ModelAttribute(name = "model") SearchBindingModel model,
                                      BindingResult bindingResult,
                                      HttpSession session) {

        if (bindingResult.hasErrors()){
            return view("home");
        }

        session.setAttribute("searched", true);
        session.setAttribute("input", model.getSearch());
        return redirect("/search?page=1");
    }
}