package jpa.hello.domain.Item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Book extends Item{
    private String author;
    private String isbn;

    @Builder
    public Book(Long id, String name, int price, int stockQuantity, String author, String isbn) {
        this.author = author;
        this.isbn = isbn;
    }
}
