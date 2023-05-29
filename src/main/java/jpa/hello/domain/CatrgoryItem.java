package jpa.hello.domain;

import jpa.hello.domain.Item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "category_item")
@Getter
@NoArgsConstructor
public class CatrgoryItem {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_item_id")
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public CatrgoryItem(Long id, Category category, Item item) {
        this.id = id;
        this.category = category;
        this.item = item;
    }
}
