package it.capozxvii.clankchampionship.model.mapper;

import it.capozxvii.clankchampionship.model.dto.PointsDto;
import it.capozxvii.clankchampionship.model.jpa.Points;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PointsMapper extends CommonMapper {

    Points toEntity(PointsDto pointsDto);

    PointsDto toDto(Points points);
}
