package it.capozxvii.clankchampionship.service;

import it.capozxvii.clankchampionship.model.dto.PlayerDto;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;


public interface IPlayerService {
    PlayerDto insertPlayer(PlayerDto player);

    PlayerDto updatePlayer(PlayerDto player);

    void deletePlayer(PlayerID playerID);

    PlayerDto getPlayerById(PlayerID playerID);
}
