package it.capozxvii.clankchampionship.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.capozxvii.clankchampionship.abstracts.AbstractServiceTest;
import it.capozxvii.clankchampionship.model.dto.PointsDto;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.Game;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Points;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PointsServiceTest extends AbstractServiceTest {

    private Player anotherGenericPlayer;
    private Player anotherGenericPlayer2;

    @BeforeAll
    void createTwoGenericPlayers() {

        anotherGenericPlayer = playerRepository.save(
                createPlayer("anotherGenericPlayer", "anotherGenericPlayer"));
        anotherGenericPlayer2 = playerRepository.save(
                createPlayer("anotherGenericPlayer2", "anotherGenericPlayer2"));
    }

    @Test
    void insertPointsTestGameExists() {
        List<PointsDto> pointsDtos = new ArrayList<>();
        pointsDtos.add(createPointsDto(5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null, CharacterEnum.YELLOW,
                                       createPlayerDto("genericPlayer", null,
                                                       genericPlayer.getId())));
        pointsDtos.add(
                createPointsDto(27, 0, 0, 0, 5, 14, 4, 0, 21, 16, 10, 12, 20, 0, null, CharacterEnum.GREY,
                                createPlayerDto("anotherGenericPlayer", null,
                                                anotherGenericPlayer.getId())));
        pointsDtos.add(
                createPointsDto(17, 6, 5, 0, 5, 42, 13, 3, 19, 18, 9, 12, 20, 0, Map.of("memoryCores", 20),
                                CharacterEnum.BLUE,
                                createPlayerDto("anotherGenericPlayer2", null,
                                                anotherGenericPlayer2.getId())));
        Game game = gameRepository.save(createGame("aLocation", genericChampionship, LocalDateTime.now()));
        assertDoesNotThrow(() -> pointsService.insertPoints(pointsDtos, game.getId(), genericChampionship.getId()));

        Optional<Game> updatedGame = gameRepository.findById(game.getId());
        assertTrue(updatedGame.isPresent());
        assertEquals(3, updatedGame.get().getPoints().size());
        Set<Points> savedPoints = updatedGame.get().getPoints();
        filterAndCheckPoints(savedPoints, CharacterEnum.YELLOW, 5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null,
                             genericPlayer.getId());
        filterAndCheckPoints(savedPoints, CharacterEnum.GREY, 27, 0, 0, 0, 5, 14, 4, 0, 21, 16, 10, 12, 20, 0, null,
                             anotherGenericPlayer.getId());
        filterAndCheckPoints(savedPoints, CharacterEnum.BLUE, 17, 6, 5, 0, 5, 42, 13, 3, 19, 18, 9, 12, 20, 0,
                             Map.of("memoryCores", 20),
                             anotherGenericPlayer2.getId());
    }

    @Test
    void updatePointsTest() {

        Points points = createPoints(5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null, CharacterEnum.GREEN,
                                     genericPlayer);

        points = pointsRepository.save(points);

        PointsDto pointsDto = createPointsDto(27, 0, 0, 0, 5, 14, 4, 0, 21, 16, 10, 12, 20, 0, null, CharacterEnum.RED,
                                              createPlayerDto("genericPlayer", "genericFullname",
                                                              genericPlayer.getId()));

        pointsDto.setId(points.getId());
        pointsService.updatePoints(pointsDto);
        Optional<Points> res = pointsRepository.findById(points.getId());
        assertTrue(res.isPresent());
        filterAndCheckPoints(Set.of(res.get()), CharacterEnum.RED, 27, 0, 0, 0, 5, 14, 4, 0, 21, 16, 10, 12, 20, 0,
                             null, genericPlayer.getId());

    }

    @Test
    void pointsOfAGameTest() {
        Points points =
                createPoints(5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null, CharacterEnum.YELLOW, genericPlayer);
        points.setGame(genericGame);
        Long yellowPointGameId = pointsRepository.save(points).getId();
        points = createPoints(5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null, CharacterEnum.GREEN,
                              anotherGenericPlayer);
        points.setGame(genericGame);
        Long greenPointGameId = pointsRepository.save(points).getId();

        List<PointsDto> pointsOfGame = pointsService.getPointsOfAGame(genericGame.getId());
        assertFalse(pointsOfGame.isEmpty());
        Optional<PointsDto> yellowPointsDto =
                pointsOfGame.stream().filter(pointsDto -> pointsDto.getId().equals(yellowPointGameId)).findFirst();
        assertTrue(yellowPointsDto.isPresent());
        checkPointsDto(CharacterEnum.YELLOW, 5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null, genericPlayer.getId(),
                       yellowPointsDto.get());

        Optional<PointsDto> greenPointsDto =
                pointsOfGame.stream().filter(pointsDto -> pointsDto.getId().equals(greenPointGameId)).findFirst();
        assertTrue(greenPointsDto.isPresent());
        checkPointsDto(CharacterEnum.GREEN, 5, 0, 0, 0, 0, 14, 8, 0, 0, 44, 0, 0, 20, 0, null,
                       anotherGenericPlayer.getId(),
                       greenPointsDto.get());

    }
}
