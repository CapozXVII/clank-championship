package it.capozxvii.clankchampionship.repository;

import it.capozxvii.clankchampionship.model.jpa.Championship;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChampionshipRepository extends JpaRepository<Championship, Long> {
    Optional<Championship> findByName(String name);
}
