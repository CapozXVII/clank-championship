package it.capozxvii.clankchampionship.controller;

import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import it.capozxvii.clankchampionship.service.IPrevisionService;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.CollectionWrapper;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/prevision")
public class PrevisionController {

    private final IPrevisionService previsionService;

    public PrevisionController(final IPrevisionService previsionService) {
        this.previsionService = previsionService;
    }

    @PostMapping(value = "/insert-previsions")
    public ResponseEntity<CollectionWrapper<PrevisionDto>> insertPrevision(
            @RequestBody final List<PrevisionDto> previsionDtos) {
        try {
            return ResponseEntity.ok(
                    CollectionWrapper.<PrevisionDto>builder()
                            .responseObject(previsionService.insertPrevisions(previsionDtos)).build());
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError()
                    .body(CollectionWrapper.<PrevisionDto>builder().message(clankChampionshipException.getMessage())
                            .build());
        }
    }
}
