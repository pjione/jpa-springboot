package jpa.hello.domain.Item;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Entity
@Getter
@NoArgsConstructor
public class Album extends Item{
    private String artist;
    private String etc;

    @Builder
    public Album(Long id, String name, int price, int stockQuantity, String artist, String etc) {
        this.artist = artist;
        this.etc = etc;
    }
}
