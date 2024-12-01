package it.capozxvii.clankchampionship.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import it.capozxvii.clankchampionship.abstracts.AbstractServiceTest;
import it.capozxvii.clankchampionship.model.dto.ChampionshipDto;
import it.capozxvii.clankchampionship.model.jpa.Championship;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import org.junit.jupiter.api.Test;

class ChampionshipServiceTest extends AbstractServiceTest {

    @Test
    void insertChampionshipTest() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime end = LocalDateTime.now().plusYears(1L);
        String name = "La casa di clank";

        ChampionshipDto championshipDto = championshipService.createChampionship(
                ChampionshipDto.builder().name(name).startingDate(now)
                        .endingDate(end).build());

        assertEquals(now, championshipDto.getStartingDate());
        assertEquals(end, championshipDto.getEndingDate());
        assertEquals(name, championshipDto.getName());
        assertNotNull(championshipDto.getId());
        assertTrue(championshipDto.getId() > 0);
    }

    @Test
    void getChampionshipByNameTest() {
        LocalDateTime now = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
        LocalDateTime end = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS).plusYears(1L);
        String name = "La casa di clank II Edizione";
        Championship championship =
                championshipRepository.save(
                        Championship.builder().endingDate(end).startingDate(now).name(name).build());
        ChampionshipDto championshipDto = assertDoesNotThrow(() -> championshipService.getChampionshipByName(name));
        assertEquals(end, championshipDto.getEndingDate());
        assertEquals(now, championshipDto.getStartingDate());
        assertEquals(name, championshipDto.getName());
        assertEquals(championship.getId(), championshipDto.getId());
    }

    @Test
    void getChampionshipByNameExceptionTest() {

        ClankChampionshipException res = assertThrows(ClankChampionshipException.class,
                () -> championshipService.getChampionshipByName("notExisting"));

        assertEquals("Championship with id [notExisting] not found", res.getMessage());
    }
}
