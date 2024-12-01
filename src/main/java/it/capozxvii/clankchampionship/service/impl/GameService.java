package it.capozxvii.clankchampionship.service.impl;

import it.capozxvii.clankchampionship.model.dto.GameDto;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Game;
import it.capozxvii.clankchampionship.model.mapper.GameMapper;
import it.capozxvii.clankchampionship.repository.ChampionshipRepository;
import it.capozxvii.clankchampionship.repository.GameRepository;
import it.capozxvii.clankchampionship.service.IGameService;
import it.capozxvii.clankchampionship.util.Utils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GameService implements IGameService {
    private final ChampionshipRepository championshipRepository;
    private final GameMapper gameMapper;
    private final GameRepository gameRepository;
    private final Utils utils;

    public GameService(final ChampionshipRepository championshipRepository, final GameMapper gameMapper,
                       final GameRepository gameRepository, final Utils utils) {
        this.championshipRepository = championshipRepository;
        this.gameMapper = gameMapper;
        this.gameRepository = gameRepository;
        this.utils = utils;
    }

    @Override
    @Transactional
    public GameDto insertGame(final GameDto gameDto) {
        Game game = gameMapper.toEntity(gameDto);
        game.setChampionship(
                utils.checkAndGetEntity(championshipRepository, Championship.class, gameDto.getChampionshipId()));
        return gameMapper.toDto(gameRepository.save(game));
    }

    @Override
    @Transactional
    public GameDto editGame(final GameDto gameDto) {
        Game game = gameMapper.toEntity(gameDto);
        game.setId(gameDto.getId());
        game.setChampionship(
                utils.checkAndGetEntity(championshipRepository, Championship.class, gameDto.getChampionshipId()));
        return gameMapper.toDto(gameRepository.save(game));
    }

    @Override
    @Transactional
    public List<GameDto> findByLocation(final String location) {
        return gameRepository.findByLocation(location).stream().map(gameMapper::toDto).collect(Collectors.toList());
    }
}
