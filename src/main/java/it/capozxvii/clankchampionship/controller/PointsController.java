package it.capozxvii.clankchampionship.controller;

import it.capozxvii.clankchampionship.model.dto.PointsDto;
import it.capozxvii.clankchampionship.service.IPointsService;
import it.capozxvii.clankchampionship.util.Message;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/points")
public class PointsController {

    private final IPointsService pointsService;

    public PointsController(final IPointsService pointsService) {
        this.pointsService = pointsService;
    }

    @PostMapping(value = "/insert-points")
    public ResponseEntity<String> insertPoints(@RequestBody final List<PointsDto> pointsDtos,
                                               @RequestParam final Long gameId,
                                               @RequestParam final Long championshipId) {
        try {
            pointsService.insertPoints(pointsDtos, gameId, championshipId);
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError().body(clankChampionshipException.getMessage());
        }
        return ResponseEntity.ok(Message.POINTS_SUCCESSFULLY_SAVED);
    }

    @PutMapping(value = "/update-points")
    public ResponseEntity<SimpleWrapper<PointsDto>> updatePoints(@RequestBody final PointsDto pointsDto) {
        try {

            return ResponseEntity.ok()
                    .body(SimpleWrapper.<PointsDto>builder().responseObject(pointsService.updatePoints(pointsDto))
                            .build());
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError().body(SimpleWrapper.<PointsDto>builder().message(
                    clankChampionshipException.getMessage()).build());
        }
    }
}
