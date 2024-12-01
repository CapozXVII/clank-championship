package it.capozxvii.clankchampionship.model.mapper;

import it.capozxvii.clankchampionship.model.dto.ChampionshipDto;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ChampionshipMapper {
    Championship toEntity(ChampionshipDto championshipDto);

    @AfterMapping
    default void linkGames(@MappingTarget Championship championship) {
        if (championship.getGames() != null) {
            championship.getGames().forEach(game -> game.setId(championship.getId()));
        }
    }

    ChampionshipDto toDto(Championship championship);
}
