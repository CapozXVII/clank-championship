package it.capozxvii.clankchampionship.util.wrapper;

import it.capozxvii.clankchampionship.model.dto.AbstractDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollectionWrapper<DTO extends AbstractDto> extends ResponseWrapper {
    private List<DTO> responseObject;
}
