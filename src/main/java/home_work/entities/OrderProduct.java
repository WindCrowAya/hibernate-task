package home_work.entities;

import home_work.entities.ids.OrderProductId;

import javax.persistence.*;

@Entity
@Table(name = "order_products")
public class OrderProduct {

    @EmbeddedId
    private OrderProductId orderProductId;

    @Column(name = "quantity_of_products", nullable = false)
    private int quantityOfProducts;

    public OrderProductId getOrderProductId() {
        return orderProductId;
    }

    public void setOrderProductId(OrderProductId orderProductId) {
        this.orderProductId = orderProductId;
    }

    public int getQuantityOfProducts() {
        return quantityOfProducts;
    }

    public void setQuantityOfProducts(int quantityOfProducts) {
        this.quantityOfProducts = quantityOfProducts;
    }
}
