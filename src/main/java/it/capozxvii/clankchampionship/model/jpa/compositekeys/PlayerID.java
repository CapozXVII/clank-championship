package it.capozxvii.clankchampionship.model.jpa.compositekeys;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PlayerID implements Serializable {

    private Long id;

    private String nickname;

}
