package jpa.hello.api;

import jpa.hello.domain.Address;
import jpa.hello.domain.Order;
import jpa.hello.domain.OrderItem;
import jpa.hello.domain.OrderStatus;
import jpa.hello.repository.OrderRepository;
import jpa.hello.repository.OrderSearch;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderRepository orderRepository;

    @GetMapping("/ordersApi")
    public Result orders(){
        List<Order> orders = orderRepository.findAllByCriteria(new OrderSearch());
        List<OrderDtoApi> collect = orders.stream()
                .map(o -> new OrderDtoApi(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }
    @GetMapping("/ordersApi2")
    public Result orders2(
            @RequestParam(value = "offset", defaultValue = "0") int offset,
            @RequestParam(value = "limit", defaultValue = "100") int limit){

        List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

        for (Order order : orders) {
            System.out.println(" " + order.getId());
        }

        List<OrderDtoApi> collect = orders.stream()
                .map(o -> new OrderDtoApi(o))
                .collect(Collectors.toList());
        return new Result(collect);
    }
    @Getter
    static class Result<T>{
        private T data;

        public Result(T data) {
            this.data = data;
        }
    }
    @Getter
    static class OrderDtoApi {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;
        public OrderDtoApi(Order o) {
            this.orderId = o.getId();
            this.name = o.getMember().getName();
            this.orderDate = o.getOrderDate();
            this.orderStatus = o.getStatus();
            this.address = o.getDelivery().getAddress();
            this.orderItems = o.getOrderItems().stream()
                    .map(orderItem -> new OrderItemDto(orderItem))
                    .collect(Collectors.toList());
        }
    }
    @Getter
    static class OrderItemDto{
        private String itemName;
        private int orderPrice;
        private int count;

        public OrderItemDto(OrderItem orderItem){
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
        }
    }
}
