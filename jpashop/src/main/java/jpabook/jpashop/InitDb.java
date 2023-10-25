package jpabook.jpashop;

import jpabook.jpashop.domain.*;
import jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init(){
        initService.dbInint1();
        initService.dbInint2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;

        public void dbInint1(){
            Member member = createMember("userA","서울","거리1","11111");
            em.persist(member);


            Book book = createBook("JPA1 BOOK",10000, 100);
            em.persist(book);

            Book book2 = createBook("JPA2 BOOK",10000, 100);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 10000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private static Book createBook(String name, int price, int quantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(quantity);
            return book;
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city,street,zipcode));
            return member;
        }

        public void dbInint2(){
            Member member = createMember("userB","대전","거리0","21111");
            em.persist(member);


            Book book = createBook("SPRING1 BOOK",20000, 200);
            em.persist(book);

            Book book2 = createBook("SPRING2 BOOK",40000, 300);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book, 20000, 3);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private static Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }
    }

}

