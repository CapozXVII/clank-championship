package it.capozxvii.clankchampionship.model.dto;

import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.Points;
import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link Points}
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PointsDto extends AbstractDto implements Serializable {
    private int artifacts;
    private int eggs;
    private int monkeys;
    private int chalices;
    private int maps;
    private int tomes;
    private int prisoners;
    private int gems;
    private int undefinedCards;
    private int money;
    private int crowns;
    private int market;
    private int mastery;
    private int trophies;
    private Map<String, Integer> otherCategories;
    private CharacterEnum characterEnum;
    private PlayerDto player;
    private GameDto game;
}
