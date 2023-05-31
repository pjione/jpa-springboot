package jpa.hello.repository;

import jpa.hello.domain.Order;
import jpa.hello.domain.OrderStatus;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager em;

    public void save(Order order){
        em.persist(order);
    }
    public Optional<Order> findById(Long id){
        return Optional.ofNullable(em.find(Order.class, id));
    }
   /* public List<Order> findAll(OrderSearch orderSearch){
        em.createQuery("select o from Order o join o.member m", Order.class);
    }*/

}
