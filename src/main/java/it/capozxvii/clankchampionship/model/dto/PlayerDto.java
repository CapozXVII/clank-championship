package it.capozxvii.clankchampionship.model.dto;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * DTO for {@link it.capozxvii.clankchampionship.model.jpa.Player}
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PlayerDto extends AbstractDto implements Serializable {
    private String nickname;
    private String fullname;
}
