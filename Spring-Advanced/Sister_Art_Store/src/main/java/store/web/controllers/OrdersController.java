package store.web.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import store.model.service.OrderProductServiceModel;
import store.model.service.OrderServiceModel;
import store.model.service.ProductServiceModel;
import store.model.view.CategoryAllViewModel;
import store.model.view.OrderViewModel;
import store.service.CategoryService;
import store.service.OrderService;
import store.service.ProductService;
import store.web.annotations.PageTitle;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/orders")
public class OrdersController extends BaseController {

    private final ProductService productService;
    private final CategoryService categoryService;
    private final OrderService orderService;
    private final ModelMapper mapper;

    public OrdersController(
            ProductService productService,
            CategoryService categoryService, OrderService orderService,
            ModelMapper modelMapper
    ) {
        this.productService = productService;
        this.categoryService = categoryService;
        this.orderService = orderService;
        this.mapper = modelMapper;
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PageTitle("All Orders")
    public ModelAndView getAllOrders(ModelAndView modelAndView) {
        List<OrderViewModel> orderViewModels = orderService.findAllOrders()
                .stream()
                .map(o -> mapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", orderViewModels);

        return view("orders/all-orders", modelAndView);
    }

    @GetMapping("/all/details/{id}")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Orders Details")
    public ModelAndView allOrderDetails(@PathVariable String id, ModelAndView modelAndView) {
        OrderServiceModel orderServiceModel = this.orderService.findOrderById(id);
        Set<OrderProductServiceModel> products = orderServiceModel.getProducts();
        modelAndView.addObject("products", products);
        OrderViewModel order = this.mapper
                .map(orderServiceModel, OrderViewModel.class);
        modelAndView.addObject("order", order);
        return view("orders/order-details", modelAndView);
    }

    @GetMapping("/customer")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Customer Orders")
    public ModelAndView getMyOrders(ModelAndView modelAndView, Principal principal) {
        List<OrderViewModel> orderViewModels = orderService.findOrdersByCustomer(principal.getName())
                .stream()
                .map(o -> mapper.map(o, OrderViewModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("orders", orderViewModels);

        return view("orders/all-orders", modelAndView);
    }

    @GetMapping("/delivery")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Delivery")
    public ModelAndView delivery(ModelAndView modelAndView) {
        List<CategoryAllViewModel> categories = this.categoryService
                .findAllCategories()
                .stream()
                .map(category -> this.mapper.map(category, CategoryAllViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("categories", categories);
        return view("orders/delivery", modelAndView);
    }
}
