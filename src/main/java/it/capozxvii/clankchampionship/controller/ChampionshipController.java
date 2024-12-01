package it.capozxvii.clankchampionship.controller;

import it.capozxvii.clankchampionship.model.dto.ChampionshipDto;
import it.capozxvii.clankchampionship.service.IChampionshipService;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/championship")
public class ChampionshipController {
    private final IChampionshipService championshipService;

    public ChampionshipController(final IChampionshipService championshipService) {
        this.championshipService = championshipService;
    }

    @PostMapping("/insert-championship")
    public ResponseEntity<SimpleWrapper<ChampionshipDto>> insertChampionship(
            @RequestBody final ChampionshipDto championshipDto) {
        try {
            return ResponseEntity.ok(SimpleWrapper.<ChampionshipDto>builder()
                    .responseObject(championshipService.createChampionship(championshipDto))
                    .build());
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body(SimpleWrapper.<ChampionshipDto>builder().message(e.getMessage()).build());
        }
    }
}
