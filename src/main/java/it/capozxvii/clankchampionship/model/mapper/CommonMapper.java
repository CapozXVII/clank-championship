package it.capozxvii.clankchampionship.model.mapper;

import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import org.mapstruct.Named;

public interface CommonMapper {
    @Named("toPlayerID")
    static PlayerID toPlayerID(Player player) {
        return PlayerID.builder().id(player.getId()).nickname(player.getNickname()).build();
    }


}
