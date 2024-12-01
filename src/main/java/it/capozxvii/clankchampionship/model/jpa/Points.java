package it.capozxvii.clankchampionship.model.jpa;

import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.converter.OtherCategoriesConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name = "points")
@Table(name = "points")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Points extends AbstractEntity {

    @Column(name = "artifacts")
    @Builder.Default
    private int artifacts = 0;

    @Column(name = "eggs")
    @Builder.Default
    private int eggs = 0;

    @Column(name = "monkeys")
    @Builder.Default
    private int monkeys = 0;

    @Column(name = "chalices")
    @Builder.Default
    private int chalices = 0;

    @Column(name = "maps")
    @Builder.Default
    private int maps = 0;

    @Column(name = "tomes")
    @Builder.Default
    private int tomes = 0;

    @Column(name = "prisoners")
    @Builder.Default
    private int prisoners = 0;

    @Column(name = "gems")
    @Builder.Default
    private int gems = 0;

    @Column(name = "undefinedCards")
    @Builder.Default
    private int undefinedCards = 0;

    @Column(name = "money")
    @Builder.Default
    private int money = 0;

    @Column(name = "crowns")
    @Builder.Default
    private int crowns = 0;

    @Column(name = "market")
    @Builder.Default
    private int market = 0;

    @Column(name = "mastery")
    @Builder.Default
    private int mastery = 0;

    @Column(name = "trophies")
    @Builder.Default
    private int trophies = 0;

    @Column(name = "other_categories")
    @Convert(converter = OtherCategoriesConverter.class)
    private Map<String, Integer> otherCategories;

    @Enumerated(EnumType.STRING)
    private CharacterEnum characterEnum;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "player_nickname", referencedColumnName = "nickname"),
            @JoinColumn(name = "player_id", referencedColumnName = "id")
    })
    private Player player;

    @ManyToOne
    @JoinColumn(name = "game")
    private Game game;

    public int getTotalPoints() {
        return
                artifacts + eggs + monkeys + chalices + maps + tomes + prisoners + gems + undefinedCards + money
                        + crowns + market + mastery + trophies + (otherCategories != null
                        ? otherCategories.values().stream().mapToInt(Integer::valueOf)
                        .sum() : 0);
    }
}
