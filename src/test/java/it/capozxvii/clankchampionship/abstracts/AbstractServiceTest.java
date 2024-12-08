package it.capozxvii.clankchampionship.abstracts;

import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Game;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.repository.ChampionshipRepository;
import it.capozxvii.clankchampionship.repository.GameRepository;
import it.capozxvii.clankchampionship.repository.PlayerRepository;
import it.capozxvii.clankchampionship.repository.PointsRepository;
import it.capozxvii.clankchampionship.repository.PrevisionRepository;
import it.capozxvii.clankchampionship.service.IChampionshipService;
import it.capozxvii.clankchampionship.service.IGameService;
import it.capozxvii.clankchampionship.service.IPlayerService;
import it.capozxvii.clankchampionship.service.IPointsService;
import it.capozxvii.clankchampionship.service.IPrevisionService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class AbstractServiceTest extends AbstractTest {

    @Autowired
    protected IChampionshipService championshipService;
    @Autowired
    protected IGameService gameService;
    @Autowired
    protected IPlayerService playerService;
    @Autowired
    protected IPointsService pointsService;
    @Autowired
    protected IPrevisionService previsionService;

    @Autowired
    protected ChampionshipRepository championshipRepository;
    @Autowired
    protected GameRepository gameRepository;
    @Autowired
    protected PointsRepository pointsRepository;
    @Autowired
    protected PlayerRepository playerRepository;
    @Autowired
    protected PrevisionRepository previsionRepository;

    protected Championship genericChampionship;

    protected Game genericGame;
    protected Player genericPlayer;


    @BeforeAll
    void createGenericChampionship() {
        championshipRepository.findByName("La casa di Clank Generic Edition")
                .ifPresentOrElse(championship -> this.genericChampionship = championship, () -> {
                    this.genericChampionship = championshipRepository.save(
                            createChampionship("La casa di Clank Generic Edition", LocalDateTime.now(),
                                               LocalDateTime.now().plusYears(1)));
                });
    }

    @BeforeAll
    void createGenericGame() {
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 1, 1);
        gameRepository.findById(1L).ifPresentOrElse(game -> this.genericGame = game, () -> {
            this.genericGame = gameRepository.save(createGame("Clank House", genericChampionship, date));
        });
    }

    @BeforeAll
    void createGenericPlayer() {
        playerRepository.findById(PlayerID.builder().nickname("genericPlayer").build()).ifPresentOrElse(player -> {
            this.genericPlayer = player;
        }, () -> this.genericPlayer = playerRepository.saveAndFlush(createPlayer("genericPlayer", "genericFullname")));
    }
}
