package it.capozxvii.clankchampionship.service;

import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import java.util.List;

public interface IPrevisionService {
    List<PrevisionDto> insertPrevisions(List<PrevisionDto> previsionDtos);

    List<PrevisionDto> editPrevision(PrevisionDto previsionDto);

}
