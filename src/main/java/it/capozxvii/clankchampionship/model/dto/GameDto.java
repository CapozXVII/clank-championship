package it.capozxvii.clankchampionship.model.dto;

import it.capozxvii.clankchampionship.model.jpa.Game;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link Game}
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class GameDto extends AbstractDto implements Serializable {
    private String location;
    private LocalDateTime gameDate;
    private Long championshipId;
}
