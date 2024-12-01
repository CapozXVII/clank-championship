package it.capozxvii.clankchampionship.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.capozxvii.clankchampionship.abstracts.AbstractServiceTest;
import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.model.jpa.Player;
import it.capozxvii.clankchampionship.model.jpa.Prevision;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PrevisionServiceTest extends AbstractServiceTest {

    @Test
    void insertPrevisionsTest() {
        Player cPlayer = playerRepository.save(createPlayer("cNickname", "fullCName"));
        Player dPlayer = playerRepository.save(createPlayer("dNickname", "fullDName"));
        Championship championship =
                championshipRepository.save(
                        createChampionship("La casa di Clank III Edizione", LocalDateTime.now().truncatedTo(
                                        ChronoUnit.SECONDS),
                                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusYears(1L)));
        List<PrevisionDto> previsions = new ArrayList<>();
        Map<Integer, CharacterEnum> chosenCharacters = new HashMap<>();
        chosenCharacters.put(1, CharacterEnum.BLUE);
        chosenCharacters.put(2, CharacterEnum.YELLOW);
        chosenCharacters.put(3, CharacterEnum.GREY);
        previsions.add(createPrevisionDto(championship.getId(),
                PlayerID.builder().nickname("cNickname").id(cPlayer.getId()).build(),
                chosenCharacters));
        chosenCharacters = new HashMap<>();
        chosenCharacters.put(1, CharacterEnum.GREY);
        chosenCharacters.put(2, CharacterEnum.BLUE);
        chosenCharacters.put(3, CharacterEnum.YELLOW);
        previsions.add(createPrevisionDto(championship.getId(),
                PlayerID.builder().nickname("dNickname").id(dPlayer.getId()).build(),
                chosenCharacters));
        List<PrevisionDto> previsionDtos = assertDoesNotThrow(() -> previsionService.insertPrevisions(previsions));
        Optional<PrevisionDto> previsionDtoOptional = previsionDtos.stream()
                .filter(previsionDto -> previsionDto.getPlayerID() != null && previsionDto.getPlayerID().getId()
                        .equals(cPlayer.getId())).findFirst();
        assertTrue(previsionDtoOptional.isPresent());

        PrevisionDto previsionDtoResult = previsionDtoOptional.get();
        assertEquals("cNickname", previsionDtoResult.getPlayerID().getNickname());
        assertEquals(championship.getId(), previsionDtoResult.getChampionshipId());
        assertEquals(CharacterEnum.BLUE, previsionDtoResult.getPredictions().get(1));
        assertEquals(CharacterEnum.YELLOW, previsionDtoResult.getPredictions().get(2));
        assertEquals(CharacterEnum.GREY, previsionDtoResult.getPredictions().get(3));

        previsionDtoOptional = previsionDtos.stream()
                .filter(previsionDto -> previsionDto.getPlayerID() != null && previsionDto.getPlayerID().getId()
                        .equals(dPlayer.getId())).findFirst();
        assertTrue(previsionDtoOptional.isPresent());

        previsionDtoResult = previsionDtoOptional.get();
        assertEquals("dNickname", previsionDtoResult.getPlayerID().getNickname());
        assertEquals(championship.getId(), previsionDtoResult.getChampionshipId());
        assertEquals(CharacterEnum.GREY, previsionDtoResult.getPredictions().get(1));
        assertEquals(CharacterEnum.BLUE, previsionDtoResult.getPredictions().get(2));
        assertEquals(CharacterEnum.YELLOW, previsionDtoResult.getPredictions().get(3));

    }

    @Test
    void insertPrevisionsTestExceptions() {
        Player ePlayer = playerRepository.save(createPlayer("eNickname", "fullEName"));
        List<PrevisionDto> previsions = new ArrayList<>();
        Map<Integer, CharacterEnum> chosenCharacters = new HashMap<>();
        chosenCharacters.put(1, CharacterEnum.BLUE);
        chosenCharacters.put(2, CharacterEnum.YELLOW);
        chosenCharacters.put(3, CharacterEnum.GREY);
        previsions.add(createPrevisionDto(-10L,
                PlayerID.builder().nickname("notExisting").id(-10L).build(),
                chosenCharacters));
        ClankChampionshipException exception =
                assertThrows(ClankChampionshipException.class, () -> previsionService.insertPrevisions(previsions));

        assertEquals("Player with id [-10, nickname notExisting] not found", exception.getMessage());
        previsions.clear();
        chosenCharacters = new HashMap<>();
        chosenCharacters.put(1, CharacterEnum.BLUE);
        chosenCharacters.put(2, CharacterEnum.YELLOW);
        chosenCharacters.put(3, CharacterEnum.GREY);
        previsions.add(createPrevisionDto(-10L,
                PlayerID.builder().nickname("eNickname").id(ePlayer.getId()).build(),
                chosenCharacters));
        exception =
                assertThrows(ClankChampionshipException.class, () -> previsionService.insertPrevisions(previsions));
        assertEquals("Championship with id [-10] not found", exception.getMessage());
    }

    @Test
    void editPrevisionTests() {
        Player fPlayer = playerRepository.save(createPlayer("fNickname", "fullFName"));
        Championship championship =
                championshipRepository.save(
                        createChampionship("La casa di Clank IV Edizione", LocalDateTime.now().truncatedTo(
                                        ChronoUnit.SECONDS),
                                LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusYears(1L)));
        Map<Integer, CharacterEnum> prediction = new HashMap<>();
        prediction.put(1, CharacterEnum.BLUE);
        prediction.put(2, CharacterEnum.YELLOW);
        prediction.put(3, CharacterEnum.GREY);
        Prevision prevision = previsionRepository.save(
                Prevision.builder().player(fPlayer).championship(championship).predictions(prediction).build());
        List<PrevisionDto> previsionDtos = assertDoesNotThrow(() -> previsionService.editPrevision(
                PrevisionDto.builder().championshipId(championship.getId()).id(prevision.getId()).playerID(
                                PlayerID.builder().nickname(fPlayer.getNickname()).id(fPlayer.getId()).build())
                        .predictions(Map.of(2, CharacterEnum.BLUE)).build()));
        Optional<PrevisionDto> dto = previsionDtos.stream().findFirst();
        assertTrue(dto.isPresent());

        Map<Integer, CharacterEnum> predictionAfterEdit = dto.get().getPredictions();
        assertEquals(3, predictionAfterEdit.size());
        assertEquals(CharacterEnum.BLUE, predictionAfterEdit.get(1));
        assertEquals(CharacterEnum.BLUE, predictionAfterEdit.get(2));
        assertEquals(CharacterEnum.GREY, predictionAfterEdit.get(3));

    }

}
