package it.capozxvii.clankchampionship.model.dto;

import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import java.io.Serializable;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link it.capozxvii.clankchampionship.model.jpa.Prevision}
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PrevisionDto extends AbstractDto implements Serializable {
    private PlayerID playerID;
    private Long championshipId;
    private Map<Integer, CharacterEnum> predictions;
}
