package jpabook.jpashop.repository.order.query;

import jpabook.jpashop.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {

    private final EntityManager em;

    public List<OrderQueryDto> findOrderQueryDto() {
        List<OrderQueryDto> result = findOrders();
        result.forEach(o->{
            List<OrderItemQeuryDto> orderItems = findOrdeerItems(o.getOrderId());
            o.setOrderItems(orderItems);
        });
        return result;
    }

    public List<OrderQueryDto> findOrderQueryDto_optimization() {
        List<OrderQueryDto> result = findOrders();

        Map<Long, List<OrderItemQeuryDto>> orderItemMap = findOrderItemMap(toOrderIds(result));

        result.forEach(o->o.setOrderItems(orderItemMap.get(o.getOrderId())));

        return result;
    }

    private Map<Long, List<OrderItemQeuryDto>> findOrderItemMap(List<Long> orderIds) {
        List<OrderItemQeuryDto> orderItems= em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQeuryDto(oi.order.id, i.name, oi.orderPrice, oi.count) from OrderItem oi"
                        + " join oi.item i"
                        + " where oi.order.id in :orderIds", OrderItemQeuryDto.class)
                .setParameter("orderIds", orderIds)
                .getResultList();

        Map<Long, List<OrderItemQeuryDto>> orderItemMap = orderItems.stream().collect(Collectors.groupingBy(orderItemQeuryDto -> orderItemQeuryDto.getOrderid()));
        return orderItemMap;
    }

    private static List<Long> toOrderIds(List<OrderQueryDto> result) {
        List<Long> orderIds = result.stream().map(o -> o.getOrderId()).collect(Collectors.toList());
        return orderIds;
    }

    private List<OrderItemQeuryDto> findOrdeerItems(Long orderId) {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderItemQeuryDto(oi.order.id, i.name, oi.orderPrice, oi.count) from OrderItem oi"
                        + " join oi.item i"
                        + " where oi.order.id = :orderId", OrderItemQeuryDto.class)
                .setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderQueryDto(o.id, m.name, o.orderDate, o.status, d.address) from Order o"
                        + " join o.member m"
                        + " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }


    public List<OrderFlatDto> findOrderQueryDto_flat() {
        return em.createQuery("select new jpabook.jpashop.repository.order.query.OrderFlatDto(o.id, m.name, o.orderDate, o.status, d.address, i.name, oi.orderPrice , oi.count) from Order o"
                + " join o.member m"
                + " join o.delivery d"
                + " join o.orderItems oi"
                + " join oi.item i", OrderFlatDto.class)
                .getResultList();
    }
}
