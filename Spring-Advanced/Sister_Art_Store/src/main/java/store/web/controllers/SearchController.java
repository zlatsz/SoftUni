package store.web.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import store.model.binding.SearchBindingModel;
import store.model.entity.Product;
import store.model.view.CategoryAllViewModel;
import store.model.view.ProductAllViewModel;
import store.service.CategoryService;
import store.service.ProductService;
import store.web.annotations.PageTitle;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/search")
public class SearchController extends BaseController {

    private final ProductService productService;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;

    @Autowired
    public SearchController(ProductService productService, ModelMapper modelMapper, CategoryService categoryService) {
        this.productService = productService;
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
    }

    @GetMapping("")
    @PageTitle(value = "Search")
    public ModelAndView search(HttpSession session, ModelAndView modelAndView) {
        if (session.getAttribute("searched") != null){

            String formattedInput = String.valueOf(session.getAttribute("input")).trim().toLowerCase();

            List<Product> products = this.productService.findAllBySearch(formattedInput);
            List<ProductAllViewModel> matchedProducts = products.stream()
                    .map(productServiceModel -> this.modelMapper.map(productServiceModel, ProductAllViewModel.class))
                    .collect(Collectors.toList());
            if(!matchedProducts.isEmpty()) {
                modelAndView.addObject("matchedProducts", matchedProducts);
            } else {
                modelAndView.addObject("notFound", true);
            }

            List<CategoryAllViewModel> categories = this.categoryService
                    .findAllCategories()
                    .stream()
                    .map(category -> this.modelMapper.map(category, CategoryAllViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("categories", categories);
            SearchBindingModel model = new SearchBindingModel();
            modelAndView.addObject("model", model);
            return view("search-result", modelAndView );
        }

        return view("home");
    }
}
