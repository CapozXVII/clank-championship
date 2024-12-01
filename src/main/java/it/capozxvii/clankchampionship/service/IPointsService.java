package it.capozxvii.clankchampionship.service;

import it.capozxvii.clankchampionship.model.dto.PointsDto;
import java.util.List;

public interface IPointsService {
    void insertPoints(List<PointsDto> pointsDtoList, Long gameId, Long championshipId);

    PointsDto updatePoints(PointsDto pointsDto);
}
