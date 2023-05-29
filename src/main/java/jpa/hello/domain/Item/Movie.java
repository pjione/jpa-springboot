package jpa.hello.domain.Item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Movie extends Item{
    private String director;
    private String actor;

    @Builder
    public Movie(Long id, String name, int price, int stockQuantity, String director, String actor) {
        this.director = director;
        this.actor = actor;
    }
}
