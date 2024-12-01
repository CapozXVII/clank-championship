package it.capozxvii.clankchampionship.model.mapper;

import it.capozxvii.clankchampionship.model.dto.GameDto;
import it.capozxvii.clankchampionship.model.jpa.Game;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GameMapper {


    Game toEntity(GameDto gameDto);

    @Mapping(source = "game.championship.id", target = "championshipId")
    GameDto toDto(Game game);
}
