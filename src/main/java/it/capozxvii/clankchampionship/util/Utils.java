package it.capozxvii.clankchampionship.util;

import it.capozxvii.clankchampionship.model.jpa.AbstractEntity;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
public class Utils {
    public <E extends AbstractEntity> E checkAndGetEntity(final JpaRepository<E, Long> repository,
                                                          final Class<E> objectClass,
                                                          final Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new ClankChampionshipException(Message.NOT_FOUND, objectClass.getSimpleName(), id));
    }

    public <E extends AbstractEntity> Optional<E> checkAndGetEntity(final JpaRepository<E, Long> repository,
                                                                    final Long id) {
        return repository.findById(id);
    }

    public Player checkAndGetPlayer(final JpaRepository<Player, PlayerID> repository,
                                    final PlayerID id) throws ClankChampionshipException {
        return repository.findById(id)
                .orElseThrow(() -> new ClankChampionshipException(Message.NOT_FOUND, Player.class.getSimpleName(),
                        id.getId() + ", nickname " + id.getNickname()));
    }

}
