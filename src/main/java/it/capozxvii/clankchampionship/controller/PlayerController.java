package it.capozxvii.clankchampionship.controller;

import it.capozxvii.clankchampionship.model.dto.PlayerDto;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.service.IPlayerService;
import it.capozxvii.clankchampionship.util.Message;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController(value = "/player")
public class PlayerController {

    private final IPlayerService playerService;

    public PlayerController(final IPlayerService playerService) {
        this.playerService = playerService;
    }

    @PostMapping("/insert-player")
    public ResponseEntity<String> insertPlayer(@RequestBody final PlayerDto playerDto) {
        try {
            playerService.insertPlayer(playerDto);
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError().body(clankChampionshipException.getMessage());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
                Message.formatMessage(Message.SUCCESSFULLY_SAVED, Player.class.getSimpleName(),
                        playerDto.getNickname()));
    }

    @PutMapping("/update-player")
    public ResponseEntity<String> updatePlayer(@RequestBody final PlayerDto playerDto) {
        try {
            playerService.updatePlayer(playerDto);
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError().body(clankChampionshipException.getMessage());
        }
        return ResponseEntity.ok(
                Message.formatMessage(Message.SUCCESSFULLY_SAVED, Player.class, playerDto.getNickname()));
    }

    @DeleteMapping("/delete-player")
    public ResponseEntity<String> deletePlayer(@RequestBody final PlayerID playerId) {
        try {
            playerService.deletePlayer(playerId);
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError().body(clankChampionshipException.getMessage());
        }
        return ResponseEntity.ok(
                Message.formatMessage(Message.SUCCESSFULLY_DELETED, Player.class, playerId.getNickname()));
    }

    @GetMapping
    public ResponseEntity<SimpleWrapper<PlayerDto>> getPlayer(@RequestBody final PlayerID playerId) {
        try {
            return ResponseEntity.ok(
                    SimpleWrapper.<PlayerDto>builder().responseObject(playerService.getPlayerById(playerId)).build());
        } catch (ClankChampionshipException clankChampionshipException) {
            return ResponseEntity.internalServerError().body(SimpleWrapper.<PlayerDto>builder().message(
                    clankChampionshipException.getMessage()).build());
        }
    }
}
