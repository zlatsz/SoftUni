package store.web;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;
import store.service.CategoryService;
import store.service.ProductService;

import java.util.stream.Collectors;

@Controller
public class HomeController {

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
    public String index(){
        return "index";
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public String home(){
        return "home";
    }
}
