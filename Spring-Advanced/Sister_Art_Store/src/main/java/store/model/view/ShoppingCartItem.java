package store.model.view;

import java.io.Serializable;

public class ShoppingCartItem {

    private OrderProductViewModel product;
    private int quantity;

    public ShoppingCartItem() {
    }

    public OrderProductViewModel getProduct() {
        return product;
    }

    public void setProduct(OrderProductViewModel product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
