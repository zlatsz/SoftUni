package store.web.controllers;


import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import store.error.OrderNotFoundException;
import store.model.service.OrderServiceModel;
import store.model.view.OrderProductViewModel;
import store.model.view.ProductDetailsViewModel;
import store.model.view.ShoppingCartItem;
import store.service.OrderService;
import store.service.ProductService;
import store.service.UserService;
import store.web.annotations.PageTitle;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController extends BaseController {

    private final ProductService productService;
    private final UserService userService;
    private final OrderService orderService;
    private final ModelMapper modelMapper;

    @Autowired
    public CartController(ProductService productService, UserService userService, OrderService orderService, ModelMapper modelMapper) {
        this.productService = productService;
        this.userService = userService;
        this.orderService = orderService;
        this.modelMapper = modelMapper;
    }


    @PostMapping("/add-product")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addToCartConfirm(String id, Integer quantity, HttpSession session) {

        if (quantity == null) {
            throw new IllegalArgumentException("Null is not an option");
        }

        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }

        ProductDetailsViewModel product = this.modelMapper
                .map(this.productService.findProductById(id), ProductDetailsViewModel.class);

        OrderProductViewModel orderProductViewModel = new OrderProductViewModel();
        orderProductViewModel.setProduct(product);
        orderProductViewModel.setPrice(product.getPrice());
        ShoppingCartItem cartItem = new ShoppingCartItem();
        cartItem.setProduct(orderProductViewModel);
        cartItem.setQuantity(quantity);

        List<ShoppingCartItem> cart = this.retrieveCart(session);
        this.addItemToCart(cartItem, cart);

        return redirect("/home");
    }

    @GetMapping("/details")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Cart Details")
    public ModelAndView cartDetails(ModelAndView modelAndView, HttpSession session) {
        List<ShoppingCartItem> cart = this.retrieveCart(session);
        modelAndView.addObject("totalPrice", this.orderService.calcTotal(cart));
        return view("cart/cart-details", modelAndView);
    }

    //    @DeleteMapping("/remove-product")
//    @PreAuthorize("isAuthenticated()")
//    public ModelAndView removeFromCartConfirm(String id, HttpSession session) {
//        this.removeItemFromCart(id, this.retrieveCart(session));
//        return redirect("/cart/details");
//    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String delete(@ModelAttribute(name = "deleteId") String deleteId, HttpSession session) {
        this.removeItemFromCart(deleteId, this.retrieveCart(session));
        return "redirect:/cart/details";
    }

    @PostMapping("/checkout")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView checkoutConfirm(HttpSession session, Principal principal) {
        List<ShoppingCartItem> cart = this.retrieveCart(session);
        OrderServiceModel orderServiceModel = this.orderService.prepareOrder(cart, principal.getName());
        if (orderServiceModel.getProducts().isEmpty()) {
            throw new OrderNotFoundException("Order not found.");
        }
        this.orderService.createOrder(orderServiceModel);
        cart.clear();
        return redirect("/orders/delivery");
    }

    private List<ShoppingCartItem> retrieveCart(HttpSession session) {
        this.initCart(session);
        return (List<ShoppingCartItem>) session.getAttribute("shopping-cart");
    }

    private void initCart(HttpSession session) {
        if (session.getAttribute("shopping-cart") == null) {
            session.setAttribute("shopping-cart", new LinkedList<>());
        }
    }

    private void addItemToCart(ShoppingCartItem item, List<ShoppingCartItem> cart) {
        for (ShoppingCartItem shoppingCartItem : cart) {
            if (shoppingCartItem.getProduct().getProduct().getId().equals(item.getProduct().getProduct().getId())) {
                shoppingCartItem.setQuantity(shoppingCartItem.getQuantity() + item.getQuantity());
                return;
            }
        }
        cart.add(item);
    }

    private void removeItemFromCart(String id, List<ShoppingCartItem> cart) {
        cart.removeIf(ci -> ci.getProduct().getProduct().getId().equals(id));
    }


}
