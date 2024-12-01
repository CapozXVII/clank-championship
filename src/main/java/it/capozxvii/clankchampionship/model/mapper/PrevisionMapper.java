package it.capozxvii.clankchampionship.model.mapper;

import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Prevision;
import java.util.Map;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PrevisionMapper extends CommonMapper {

    Prevision toEntity(Player player, Championship championship, Map<Integer, CharacterEnum> predictions);

    @Mapping(source = "prevision.championship.id", target = "championshipId")
    @Mapping(source = "prevision.player", target = "playerID", qualifiedByName = "toPlayerID")
    PrevisionDto toDto(Prevision prevision);
}
