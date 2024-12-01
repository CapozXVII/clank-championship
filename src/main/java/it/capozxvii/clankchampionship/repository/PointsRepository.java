package it.capozxvii.clankchampionship.repository;

import it.capozxvii.clankchampionship.model.jpa.Points;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PointsRepository extends JpaRepository<Points, Long> {
}
