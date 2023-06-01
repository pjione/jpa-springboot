package jpa.hello.controller;


import jpa.hello.domain.Item.Book;
import jpa.hello.domain.Item.Item;
import jpa.hello.service.ItemService;
import jpa.hello.service.MemberService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;


import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;
    @GetMapping("/items/new")
    public String createForm(Model model){
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }
    @PostMapping("items/new")
    public String create(BookForm form){
        Book book = Book.builder()
                .name(form.getName())
                .price(form.getPrice())
                .stockQuantity(form.getStockQuantity())
                .author(form.getAuthor())
                .isbn(form.getIsbn())
                .build();

        itemService.saveItem(book);
        return "redirect:/";
    }
    @GetMapping("items")
    public String list(Model model){
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model){
        Book book = (Book) itemService.findItem(itemId).orElseThrow();
        BookForm bookForm = new BookForm(book.getId(), book.getName(), book.getPrice(), book.getStockQuantity(), book.getAuthor(), book.getIsbn());
        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }
    @PostMapping("/items/{itemId}/edit")
    public String update(@PathVariable("itemId") Long itemId, @ModelAttribute("form") BookForm form){
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return "redirect:/items";
    }
}
