package it.capozxvii.clankchampionship.service;

import it.capozxvii.clankchampionship.model.dto.GameDto;
import java.util.List;

public interface IGameService {
    GameDto insertGame(GameDto gameDto);

    GameDto editGame(GameDto gameDto);

    List<GameDto> findByLocation(String location);
}
