package jpa.hello.repository;

import jpa.hello.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.swing.text.html.parser.Entity;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager em;

    public void save(Item item){
        if(item.getId() == null){
            em.persist(item);
        } else{
            em.merge(item);
        }
    }
    public Optional<Item> findById(Long id){
        return Optional.ofNullable(em.find(Item.class, id));
    }
    public List<Item> findAll(){
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
