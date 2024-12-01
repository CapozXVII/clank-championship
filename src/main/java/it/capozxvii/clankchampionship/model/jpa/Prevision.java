package it.capozxvii.clankchampionship.model.jpa;

import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.converter.PredictedCharsForGamesConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
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

@Entity(name = "prevision")
@Table(name = "prevision")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prevision extends AbstractEntity {

    @Convert(converter = PredictedCharsForGamesConverter.class)
    private Map<Integer, CharacterEnum> predictions;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "player_nickname", referencedColumnName = "nickname"),
            @JoinColumn(name = "player_id", referencedColumnName = "id")
    })
    private Player player;

    @ManyToOne
    @JoinColumn(name = "championship")
    private Championship championship;
}
