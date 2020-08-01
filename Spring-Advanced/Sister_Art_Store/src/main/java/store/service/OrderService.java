package store.service;


import store.model.service.OrderServiceModel;

import java.util.List;

public interface OrderService {

//    BigDecimal calcTotal(List<ShoppingCartItem> cart);
//
//    OrderServiceModel prepareOrder(List<ShoppingCartItem> cart, String customer);

    void createOrder(OrderServiceModel cartServiceModel);

    List<OrderServiceModel> findAllOrders();

    List<OrderServiceModel> findOrdersByCustomer(String username);

    OrderServiceModel findOrderById(String id);

}
