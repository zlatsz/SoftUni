package store.web.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import store.model.entity.Product;
import store.model.service.ProductServiceModel;
import store.model.service.UserServiceModel;
import store.model.view.CategoryAllViewModel;
import store.model.view.ProductAllViewModel;
import store.model.view.ProductDetailsViewModel;
import store.model.view.ProductViewIndexModel;
import store.service.CategoryService;
import store.service.ProductService;
import store.web.annotations.PageTitle;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController {

    private final CategoryService categoryService;
    private final ProductService productService;
    private final ModelMapper modelMapper;

    @Autowired
    public HomeController(CategoryService categoryService, ProductService productService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.modelMapper = modelMapper;
    }


    @GetMapping("/")
    @PreAuthorize("isAnonymous()")
    @PageTitle("Index")
    public ModelAndView index(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        List<ProductAllViewModel> products = this.productService
                .findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .limit(4)
                .collect(Collectors.toList());
//        List<ProductViewIndexModel> products = this.productService.indexView();
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("products", products);
        return view("index", modelAndView);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Home")
    public ModelAndView home(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        List<ProductAllViewModel> products = this.productService
                .findAllProducts()
                .stream()
                .map(p -> this.modelMapper.map(p, ProductAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("products", products);
        return view("home", modelAndView);
    }

//    @GetMapping("/api/search")
//    @ResponseBody
//    @PreAuthorize("isAnonymous()")
//    public ProductViewModel allUsers(@Param("keyword") String name) {
//
//        List<Product> products = this.productService.listAll(name);
//
//        return products == null ? new ProductViewModel()
//                : this.modelMapper.map(products, ProductViewModel.class);
//    }

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword) {
        List<Product> products = this.productService.listAll(keyword);
        model.addAttribute("products", products);
        model.addAttribute("keyword", keyword);

        return "search-result";
    }

    @GetMapping("/aboutSisters")
    @PreAuthorize("isAnonymous()")
    @PageTitle("About Us")
    public String viewAboutUs(){
        return "about-us";
    }

    @GetMapping("/aboutOil")
    @PreAuthorize("isAnonymous()")
    @PageTitle("About Oil")
    public String viewAboutOil(){
        return "about-oil";
    }
}
