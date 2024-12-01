package it.capozxvii.clankchampionship.service.impl;

import it.capozxvii.clankchampionship.model.dto.ChampionshipDto;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.mapper.ChampionshipMapper;
import it.capozxvii.clankchampionship.repository.ChampionshipRepository;
import it.capozxvii.clankchampionship.service.IChampionshipService;
import it.capozxvii.clankchampionship.util.Message;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChampionshipService implements IChampionshipService {

    private final ChampionshipRepository championshipRepository;

    private final ChampionshipMapper championshipMapper;

    public ChampionshipService(final ChampionshipRepository championshipRepository,
                               final ChampionshipMapper championshipMapper) {
        this.championshipRepository = championshipRepository;
        this.championshipMapper = championshipMapper;
    }

    @Override
    @Transactional
    public ChampionshipDto createChampionship(final ChampionshipDto championshipDto) {
        return championshipMapper.toDto(championshipRepository.save(championshipMapper.toEntity(championshipDto)));
    }

    @Override
    @Transactional
    public ChampionshipDto getChampionshipByName(final String championshipName) throws ClankChampionshipException {
        Championship championship = championshipRepository.findByName(championshipName).orElseThrow(
                () -> new ClankChampionshipException(Message.NOT_FOUND, Championship.class.getSimpleName(),
                        championshipName));
        return championshipMapper.toDto(championship);
    }
}
