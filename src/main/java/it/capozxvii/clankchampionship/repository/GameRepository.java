package it.capozxvii.clankchampionship.repository;

import it.capozxvii.clankchampionship.model.jpa.Game;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
    List<Game> findByLocation(String location);
}
