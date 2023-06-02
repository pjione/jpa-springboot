package jpa.hello.api;

import jpa.hello.domain.Address;
import jpa.hello.domain.Order;
import jpa.hello.domain.OrderStatus;
import jpa.hello.repository.OrderRepository;
import jpa.hello.repository.OrderSearch;
import jpa.hello.service.MemberService;
import jpa.hello.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final MemberService memberService;

    @GetMapping("api/orders")
    public List<OrderDto> orders(){
        List<Order> orders = orderService.findOrders((new OrderSearch()));
        List<OrderDto> collect = orders.stream()
                .map(o -> new OrderDto(o.getId(),
                        o.getMember().getName(),
                        o.getOrderDate(),
                        o.getStatus(),
                        o.getDelivery().getAddress()))
                .collect(Collectors.toList());
        return collect;
    }
    @Data
    @AllArgsConstructor
    static class OrderDto{
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
    }
}
