package it.capozxvii.clankchampionship.repository;

import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<Player, PlayerID> {
}
