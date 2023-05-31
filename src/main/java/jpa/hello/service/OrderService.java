package jpa.hello.service;

import jpa.hello.domain.Delivery;
import jpa.hello.domain.Item.Item;
import jpa.hello.domain.Member;
import jpa.hello.domain.Order;
import jpa.hello.domain.OrderItem;
import jpa.hello.repository.ItemRepository;
import jpa.hello.repository.MemberRepository;
import jpa.hello.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    /**
     * 주문
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count){
        //엔티티 조회
        Member member = memberRepository.findById(memberId).orElseThrow();
        Item item = itemRepository.findById(itemId).orElseThrow();

        //배송정보 생성
        Delivery delivery = Delivery.builder()
                .address(member.getAddress())
                .build();

        //주문상품생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);


        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);



        orderRepository.save(order);

        return order.getId();
    }

    //취소
    @Transactional
    public void cancelOrder(Long orderId){
        //엔티티 조회
        Order order = orderRepository.findById(orderId).orElseThrow();
        //주문 취소
        order.cancel();
    }
    //검색
    /*public List<Order> findOrders(OrderSeach orderSeach){

    }*/
}
