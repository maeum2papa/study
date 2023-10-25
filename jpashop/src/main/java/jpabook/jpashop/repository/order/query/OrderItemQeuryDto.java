package jpabook.jpashop.repository.order.query;

import lombok.Data;

@Data
public class OrderItemQeuryDto {

    private Long orderid;
    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemQeuryDto(Long orderid, String itemName, int orderPrice, int count) {
        this.orderid = orderid;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
