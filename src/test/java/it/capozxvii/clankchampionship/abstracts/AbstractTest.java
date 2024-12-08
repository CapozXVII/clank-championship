package it.capozxvii.clankchampionship.abstracts;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.capozxvii.clankchampionship.model.dto.GameDto;
import it.capozxvii.clankchampionship.model.dto.PlayerDto;
import it.capozxvii.clankchampionship.model.dto.PointsDto;
import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Game;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Points;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractTest {
    protected static void filterAndCheckGameDtos(final List<GameDto> gameDtos, final Game game) {
        Optional<GameDto> gameDtoOptional =
                gameDtos.stream().filter(gameDto -> Objects.equals(gameDto.getId(), game.getId())).findFirst();

        assertTrue(gameDtoOptional.isPresent());
        GameDto gameDto = gameDtoOptional.get();
        assertEquals(game.getId(), gameDto.getId());
        assertEquals(game.getLocation(), gameDto.getLocation());
        assertEquals(game.getGameDate(), gameDto.getGameDate());
    }

    protected Championship createChampionship(final String name, final LocalDateTime start, final LocalDateTime end) {
        return Championship.builder().name(name).startingDate(start).endingDate(end).build();
    }

    protected Game createGame(final String location, final Championship championship, final LocalDateTime gameDate) {
        return Game.builder().location(location).championship(championship).gameDate(gameDate).build();
    }

    protected Player createPlayer(final String nickname, final String fullname) {
        return Player.builder().nickname(nickname).fullname(fullname).build();
    }

    protected Points createPoints(final int artifacts,
            final int eggs,
            final int monkeys,
            final int chalices,
            final int maps,
            final int tomes,
            final int prisoners,
            final int gems,
            final int undefinedCards,
            final int trophies,
            final int money,
            final int crowns,
            final int market,
            final int mastery,
            final Map<String, Integer> otherCategories,
            final CharacterEnum characterEnum,
            final Player player) {
        return Points.builder().artifacts(artifacts).eggs(eggs).monkeys(monkeys).chalices(chalices).maps(maps)
                .tomes(tomes).prisoners(prisoners).gems(gems).undefinedCards(undefinedCards).money(money)
                .crowns(crowns).market(market).mastery(mastery).trophies(trophies).otherCategories(otherCategories)
                .characterEnum(characterEnum).player(player).build();
    }

    protected GameDto createGameDto(final LocalDateTime gameDate, final String location, final Long championshipId) {
        return GameDto.builder().gameDate(gameDate).location(location)
                .championshipId(championshipId).build();
    }

    protected PlayerDto createPlayerDto(final String nickname, final String fullname, final Long id) {
        return PlayerDto.builder().nickname(nickname).fullname(fullname).id(id).build();
    }

    protected PointsDto createPointsDto(final int artifacts,
            final int eggs,
            final int monkeys,
            final int chalices,
            final int maps,
            final int tomes,
            final int prisoners,
            final int gems,
            final int undefinedCards,
            final int trophies,
            final int money,
            final int crowns,
            final int market,
            final int mastery,
            final Map<String, Integer> otherCategories,
            final CharacterEnum characterEnum,
            final PlayerDto playerDto) {
        return PointsDto.builder().artifacts(artifacts).eggs(eggs).monkeys(monkeys).chalices(chalices).maps(maps)
                .tomes(tomes).prisoners(prisoners).gems(gems).undefinedCards(undefinedCards).money(money)
                .crowns(crowns).market(market).mastery(mastery).trophies(trophies).otherCategories(otherCategories)
                .characterEnum(characterEnum).player(playerDto).build();
    }

    protected PrevisionDto createPrevisionDto(final Long championshipId,
            final PlayerID playerID,
            final Map<Integer, CharacterEnum> chosenCharacters) {
        return PrevisionDto.builder().championshipId(championshipId).predictions(chosenCharacters)
                .playerID(playerID).build();
    }

    protected void filterAndCheckPoints(final Set<Points> points,
            final CharacterEnum characterEnum,
            final int artifacts,
            final int eggs,
            final int monkeys,
            final int chalices,
            final int maps,
            final int tomes,
            final int prisoners,
            final int gems,
            final int undefinedCards,
            final int trophies,
            final int money,
            final int crowns,
            final int market,
            final int mastery,
            final Map<String, Integer> otherCategories,
            final Long playerId) {

        Optional<Points> pointsOpt = points.stream().filter(point -> point.getCharacterEnum().equals(characterEnum))
                .findFirst();
        assertTrue(pointsOpt.isPresent());
        Points point = pointsOpt.get();
        assertEquals(playerId, point.getPlayer().getId());
        assertEquals(artifacts, point.getArtifacts());
        assertEquals(eggs, point.getEggs());
        assertEquals(monkeys, point.getMonkeys());
        assertEquals(chalices, point.getChalices());
        assertEquals(maps, point.getMaps());
        assertEquals(tomes, point.getTomes());
        assertEquals(prisoners, point.getPrisoners());
        assertEquals(gems, point.getGems());
        assertEquals(undefinedCards, point.getUndefinedCards());
        assertEquals(money, point.getMoney());
        assertEquals(crowns, point.getCrowns());
        assertEquals(market, point.getMarket());
        assertEquals(mastery, point.getMastery());
        assertEquals(trophies, point.getTrophies());
        assertEquals(otherCategories, point.getOtherCategories());
        assertEquals(artifacts + eggs + monkeys + chalices + maps + tomes + prisoners + gems + undefinedCards + money
                     + crowns + market + mastery + trophies + (otherCategories != null
                                                               ? otherCategories.values().stream()
                                                                       .mapToInt(Integer::valueOf)
                                                                       .sum() : 0), point.getTotalPoints());
    }

    protected void checkPointsDto(
            final CharacterEnum character,
            final int artifacts,
            final int eggs,
            final int monkeys,
            final int chalices,
            final int maps,
            final int tomes,
            final int prisoners,
            final int gems,
            final int undefinedCards,
            final int trophies,
            final int money,
            final int crowns,
            final int market,
            final int mastery,
            final Map<String, Integer> otherCategories,
            final Long playerId,
            final PointsDto pointsDto) {
        assertEquals(character, pointsDto.getCharacterEnum());
        assertEquals(playerId, pointsDto.getPlayer().getId());
        assertEquals(artifacts, pointsDto.getArtifacts());
        assertEquals(eggs, pointsDto.getEggs());
        assertEquals(monkeys, pointsDto.getMonkeys());
        assertEquals(chalices, pointsDto.getChalices());
        assertEquals(maps, pointsDto.getMaps());
        assertEquals(tomes, pointsDto.getTomes());
        assertEquals(prisoners, pointsDto.getPrisoners());
        assertEquals(gems, pointsDto.getGems());
        assertEquals(undefinedCards, pointsDto.getUndefinedCards());
        assertEquals(money, pointsDto.getMoney());
        assertEquals(crowns, pointsDto.getCrowns());
        assertEquals(market, pointsDto.getMarket());
        assertEquals(mastery, pointsDto.getMastery());
        assertEquals(trophies, pointsDto.getTrophies());
        assertEquals(otherCategories, pointsDto.getOtherCategories());
        assertEquals(artifacts + eggs + monkeys + chalices + maps + tomes + prisoners + gems + undefinedCards + money
                     + crowns + market + mastery + trophies + (otherCategories != null
                                                               ? otherCategories.values().stream()
                                                                       .mapToInt(Integer::valueOf)
                                                                       .sum() : 0), pointsDto.getTotalPoints());
    }

}
