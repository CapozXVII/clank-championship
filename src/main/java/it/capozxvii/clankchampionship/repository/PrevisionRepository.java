package it.capozxvii.clankchampionship.repository;

import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Prevision;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrevisionRepository extends JpaRepository<Prevision, Long> {
    List<Prevision> getPrevisionsByPlayerAndChampionship(Player player, Championship championship);
}
