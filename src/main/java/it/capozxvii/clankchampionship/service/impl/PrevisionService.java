package it.capozxvii.clankchampionship.service.impl;

import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Prevision;
import it.capozxvii.clankchampionship.model.mapper.PrevisionMapper;
import it.capozxvii.clankchampionship.repository.ChampionshipRepository;
import it.capozxvii.clankchampionship.repository.PlayerRepository;
import it.capozxvii.clankchampionship.repository.PrevisionRepository;
import it.capozxvii.clankchampionship.service.IPrevisionService;
import it.capozxvii.clankchampionship.util.Utils;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PrevisionService implements IPrevisionService {

    private final PlayerRepository playerRepository;

    private final ChampionshipRepository championshipRepository;

    private final PrevisionRepository previsionRepository;

    private final PrevisionMapper previsionMapper;

    private final Utils utils;

    public PrevisionService(final PlayerRepository playerRepository,
                            final ChampionshipRepository championshipRepository,
                            final PrevisionRepository previsionRepository,
                            final PrevisionMapper previsionMapper,
                            final Utils utils) {
        this.playerRepository = playerRepository;
        this.championshipRepository = championshipRepository;
        this.previsionRepository = previsionRepository;
        this.utils = utils;
        this.previsionMapper = previsionMapper;
    }

    @Override
    @Transactional
    public List<PrevisionDto> insertPrevisions(final List<PrevisionDto> previsionDtos) {
        return previsionDtos.stream().map(previsionDto -> {
            Player player = utils.checkAndGetPlayer(playerRepository, previsionDto.getPlayerID());
            Championship championship = utils.checkAndGetEntity(championshipRepository, Championship.class,
                    previsionDto.getChampionshipId());

            return previsionMapper.toDto(previsionRepository.save(
                    previsionMapper.toEntity(player, championship, previsionDto.getPredictions())));

        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public List<PrevisionDto> editPrevision(final PrevisionDto previsionDto) {
        Player player = utils.checkAndGetPlayer(playerRepository, previsionDto.getPlayerID());
        Championship championship = utils.checkAndGetEntity(championshipRepository, Championship.class,
                previsionDto.getChampionshipId());
        Prevision prevision = utils.checkAndGetEntity(previsionRepository, Prevision.class, previsionDto.getId());
        prevision.getPredictions().putAll(previsionDto.getPredictions());
        return List.of(previsionMapper.toDto(previsionRepository.save(
                previsionMapper.toEntity(player, championship, prevision.getPredictions()))));
    }
}
