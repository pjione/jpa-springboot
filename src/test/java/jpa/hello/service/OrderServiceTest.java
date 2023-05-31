package jpa.hello.service;

import jpa.hello.domain.Address;
import jpa.hello.domain.Item.Book;
import jpa.hello.domain.Item.Item;
import jpa.hello.domain.Member;
import jpa.hello.domain.Order;
import jpa.hello.domain.OrderStatus;
import jpa.hello.exception.NotEnoughStockException;
import jpa.hello.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;



import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@Transactional
class OrderServiceTest {
    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    void 상품주문(){
        //given
        Member member = getMember();

        Item item = getItem("책1", 10000, 10);

        //when
        Long orderId = orderService.order(member.getId(), item.getId(), 2);

        //then
        Order getOrder = orderRepository.findById(orderId).orElseThrow();

        assertThat(OrderStatus.ORDER).isEqualTo(getOrder.getStatus());
        assertThat(1).isEqualTo(getOrder.getOrderItems().size());
        assertThat(10000 * 2).isEqualTo(getOrder.getTotalPrice());
        assertThat(8).isEqualTo(item.getStockQuantity());
    }
    @Test
    void 주문취소(){
        Member member = getMember();
        Item book = getItem("책1", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), 2);

        //when
        orderService.cancelOrder(orderId);

        //then
        Order order = orderRepository.findById(orderId).orElseThrow();
        assertThat(OrderStatus.CANCEL).isEqualTo(order.getStatus());
        assertThat(10).isEqualTo(book.getStockQuantity());

    }
    @Test
    void 상품주문_재고수량초과(){
        Member member = getMember();
        Item item = getItem("책1", 10000, 10);
        int orderCount = 11;

        assertThrows(NotEnoughStockException.class, () -> orderService.order(member.getId(), item.getId(), orderCount));
    }

    private Item getItem(String name, int price, int stockQuantity) {
        Item item = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
        em.persist(item);
        return item;
    }

    private Member getMember() {
        Member member = Member.builder()
                .name("회원1")
                .address(Address.builder()
                        .city("서울")
                        .street("강가")
                        .zipcode("123-123")
                        .build())
                .build();
        em.persist(member);
        return member;
    }

}