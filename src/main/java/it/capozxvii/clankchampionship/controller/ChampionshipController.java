package it.capozxvii.clankchampionship.controller;

import it.capozxvii.clankchampionship.model.dto.ChampionshipDto;
import it.capozxvii.clankchampionship.service.impl.ChampionshipService;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/championship")
public class ChampionshipController {
    private final ChampionshipService championshipService;

    public ChampionshipController(final ChampionshipService championshipService) {
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
