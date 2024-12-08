package it.capozxvii.clankchampionship.service.impl;

import it.capozxvii.clankchampionship.model.dto.PlayerDto;
import it.capozxvii.clankchampionship.model.dto.PointsDto;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Game;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Points;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.model.mapper.PointsMapper;
import it.capozxvii.clankchampionship.repository.ChampionshipRepository;
import it.capozxvii.clankchampionship.repository.GameRepository;
import it.capozxvii.clankchampionship.repository.PlayerRepository;
import it.capozxvii.clankchampionship.repository.PointsRepository;
import it.capozxvii.clankchampionship.service.IPointsService;
import it.capozxvii.clankchampionship.util.Utils;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PointsService implements IPointsService {

    private static final Logger LOG = LoggerFactory.getLogger(PointsService.class);

    private final PlayerRepository playerRepository;

    private final GameRepository gameRepository;

    private final PointsRepository pointsRepository;

    private final ChampionshipRepository championshipRepository;

    private final Utils utils;

    private final PointsMapper pointsMapper;


    public PointsService(final PlayerRepository playerRepository, final GameRepository gameRepository,
            final PointsRepository pointsRepository, final ChampionshipRepository championshipRepository,
            final Utils utils, final PointsMapper pointsMapper) {
        this.playerRepository = playerRepository;
        this.gameRepository = gameRepository;
        this.pointsRepository = pointsRepository;
        this.championshipRepository = championshipRepository;
        this.utils = utils;
        this.pointsMapper = pointsMapper;
    }

    @Override
    @Transactional
    public void insertPoints(final List<PointsDto> pointsDtoList, final Long gameId, final Long championshipId)
            throws ClankChampionshipException {
        Championship championship = utils.checkAndGetEntity(championshipRepository, Championship.class, championshipId);
        Game game = utils.checkAndGetEntity(gameRepository, gameId).orElseGet(() -> gameRepository.save(
                Game.builder().championship(championship).gameDate(LocalDateTime.now()).build()));
        pointsDtoList.forEach(pointsDto -> {
            PlayerDto playerDto = pointsDto.getPlayer();
            Player player = utils.checkAndGetPlayer(playerRepository,
                                                    PlayerID.builder().id(playerDto.getId())
                                                            .nickname(playerDto.getNickname()).build());
            Points points = pointsMapper.toEntity(pointsDto);
            points.setGame(game);
            points.setPlayer(player);
            pointsRepository.save(points);
        });
    }

    @Override
    @Transactional
    public PointsDto updatePoints(final PointsDto pointsDto) {
        Points toUpdate = pointsMapper.toEntity(pointsDto);
        toUpdate.setId(pointsDto.getId());
        Player player = utils.checkAndGetPlayer(playerRepository,
                                                PlayerID.builder().id(pointsDto.getPlayer().getId())
                                                        .nickname(pointsDto.getPlayer().getNickname()).build());
        toUpdate.setPlayer(player);
        return pointsMapper.toDto(pointsRepository.save(toUpdate));
    }

    @Override
    public List<PointsDto> getPointsOfAGame(final Long gameId) {
        return pointsRepository.findByGame(utils.checkAndGetEntity(gameRepository, Game.class, gameId)).stream()
                .map(pointsMapper::toDto).collect(Collectors.toList());
    }
}
