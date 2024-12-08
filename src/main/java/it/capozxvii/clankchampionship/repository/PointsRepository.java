package it.capozxvii.clankchampionship.repository;

import it.capozxvii.clankchampionship.model.jpa.Game;
import it.capozxvii.clankchampionship.model.jpa.Points;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsRepository extends JpaRepository<Points, Long> {
    List<Points> findByGame(Game game);
}
