package it.capozxvii.clankchampionship.model.mapper;

import it.capozxvii.clankchampionship.model.dto.PlayerDto;
import it.capozxvii.clankchampionship.model.jpa.Player;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PlayerMapper {


    Player toEntity(PlayerDto playerDto);

    PlayerDto toDto(Player player);

}
