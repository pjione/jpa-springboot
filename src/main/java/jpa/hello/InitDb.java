package jpa.hello;

import jpa.hello.domain.*;
import jpa.hello.domain.Item.Book;
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
        initService.dbInit1();
        initService.dbInit2();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService{
        private final EntityManager em;
        public void dbInit1(){
            Member member = createMember("userA", "서울", "1", "1111");
            em.persist(member);

            Book book1 = createBook("jpa1", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("jpa2", 20000, 1000);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private static Book createBook(String name, int price, int stockQuantity) {
            return Book.builder()
                    .name(name)
                    .price(price)
                    .stockQuantity(stockQuantity)
                    .build();
        }

        private static Member createMember(String name, String city, String street, String zipcode) {
            return Member.builder()
                    .name(name)
                    .address(Address.builder()
                            .city(city)
                            .street(street)
                            .zipcode(zipcode)
                            .build())
                    .build();
        }

        public void dbInit2(){
            Member member = createMember("userB", "부산", "2", "2222");
            em.persist(member);

            Book book1 = createBook("spring1", 10000, 100);
            em.persist(book1);

            Book book2 = createBook("spring2", 20000, 1000);
            em.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, book1.getPrice(), 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, book2.getPrice(), 2);

            Delivery delivery = createDelivery(member);

            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            em.persist(order);

        }

        private static Delivery createDelivery(Member member) {
            return Delivery.builder()
                    .address(member.getAddress())
                    .build();
        }
    }
}

