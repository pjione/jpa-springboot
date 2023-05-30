package jpa.hello.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Order {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "delivery_id")
    private Delivery delivery;
    private LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public void addMember(Member member){
      /*  if(this.member != null){
            this.member.getOrders().remove(this);
        }*/
        this.member = member;
        member.getOrders().add(this);
    }
    public void addOrderItems(OrderItem orderItem){
       this.orderItems.add(orderItem);
        orderItem.setOrder(this);
       /* if(orderItem.getOrder() != this){
           orderItem.setOrder(this);
       }*/
    }
    public void addDelivery(Delivery delivery){
        this.delivery = delivery;
        delivery.setOrder(this);
    }
    @Builder
    protected Order(Long id, Member member, List<OrderItem> orderItems, Delivery delivery, LocalDateTime orderDate, OrderStatus status) {
        this.id = id;
        this.member = member;
        this.orderItems = orderItems;
        this.delivery = delivery;
        this.orderDate = orderDate;
        this.status = status;
    }
    //생성메서드
    public static Order createOrder(Member member, Delivery delivery, OrderItem... orderItems){
        Order order = Order.builder()
                .status(OrderStatus.ORDER)
                .orderDate(LocalDateTime.now())
                .build();

        /*for (OrderItem orderItem : orderItems) {
            order.addOrderItems(orderItem);
        }*/
        order.addMember(member);
        order.addDelivery(delivery);
        Arrays.stream(orderItems).forEach(order::addOrderItems);
        return order;
    }
    //비즈니스 로직
    //주문 취소
    public void cancel(){
        if(delivery.getStatus() == DeliveryStatus.COMP) {
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        }
        this.setStatus(OrderStatus.CANCEL);
        /*for (OrderItem orderItem : orderItems) {
            orderItem.cancel();
        }
        */
        orderItems.forEach(OrderItem::cancel);
    }
    //조회 로직
    //전체 주문 가격 조회
    public int getTotalPrice(){
        /*int totalPrice = 0;
        for (OrderItem orderItem : orderItems) {
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;*/
        return orderItems.stream()
                .mapToInt(OrderItem::getTotalPrice)
                .sum();
    }

    private void setId(Long id) {
        this.id = id;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setOrderItems(List<OrderItem> orderItems) {
        this.orderItems = orderItems;
    }

    private void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    private void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    private void setStatus(OrderStatus status) {
        this.status = status;
    }
}
