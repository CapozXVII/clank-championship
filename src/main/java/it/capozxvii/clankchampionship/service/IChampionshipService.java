package it.capozxvii.clankchampionship.service;

import it.capozxvii.clankchampionship.model.dto.ChampionshipDto;

public interface IChampionshipService {
    ChampionshipDto createChampionship(ChampionshipDto championshipDto);

    ChampionshipDto getChampionshipByName(String championshipName);
}
