package jpa.hello.service;

import jpa.hello.domain.Item.Book;
import jpa.hello.domain.Item.Item;
import jpa.hello.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemRepository itemRepository;
    @Transactional
    public void saveItem(Item item){
        itemRepository.save(item);
    }
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity){
        Item item = itemRepository.findById(itemId).orElseThrow();
        item.setPrice(price);
        item.setName(name);
        item.setStockQuantity(stockQuantity);
    }
    public List<Item> findItems(){
        return itemRepository.findAll();
    }
    public Optional<Item> findItem(Long itemId){
        return itemRepository.findById(itemId);
    }


}
