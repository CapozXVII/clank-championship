package it.capozxvii.clankchampionship.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import it.capozxvii.clankchampionship.abstracts.AbstractControllerTest;
import it.capozxvii.clankchampionship.model.dto.GameDto;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.CollectionWrapper;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@WebMvcTest(GameController.class)
class GameControllerTest extends AbstractControllerTest {

    @Test
    void insertGame() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        GameDto gameDto = createGameDto(now, "Clank House", 1L);

        when(gameService.insertGame(gameDto)).thenAnswer(im -> {
            gameDto.setId(1L);
            return gameDto;
        });

        GameDto res = MAPPER.readValue(mvc.perform(post("/insert-game").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(gameDto))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), new TypeReference<SimpleWrapper<GameDto>>() {
        }).getResponseObject();

        assertEquals(1L, res.getId());
        assertEquals(now, res.getGameDate());
        assertEquals("Clank House", res.getLocation());
        assertEquals(1L, res.getChampionshipId());
    }

    @Test
    void insertGameChampionshipNotExistingTest() throws Exception {

        LocalDateTime now = LocalDateTime.now();

        GameDto gameDto = createGameDto(now, "Clank House", 111111111L);
        doThrow(new ClankChampionshipException("Championship with id [111111111] not found")).when(gameService)
                .insertGame(gameDto);

        String res = MAPPER.readValue(mvc.perform(post("/insert-game").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(gameDto))).andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString(), new TypeReference<SimpleWrapper<GameDto>>() {
        }).getMessage();
        assertEquals("Championship with id [111111111] not found", res);
    }

    @Test
    void editGameTest() throws Exception {
        LocalDateTime now = LocalDateTime.now();

        GameDto gameDto = createGameDto(now, "Clank House", 1L);

        when(gameService.editGame(gameDto)).thenAnswer(im -> {
            gameDto.setId(1L);
            return gameDto;
        });

        GameDto res = MAPPER.readValue(mvc.perform(put("/edit-game").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(gameDto))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), new TypeReference<SimpleWrapper<GameDto>>() {
        }).getResponseObject();

        assertEquals(1L, res.getId());
        assertEquals(now, res.getGameDate());
        assertEquals("Clank House", res.getLocation());
        assertEquals(1L, res.getChampionshipId());
    }

    @Test
    void editGameChampionshipNotExistingTest() throws Exception {

        LocalDateTime now = LocalDateTime.now();

        GameDto gameDto = createGameDto(now, "Clank House", 111111111L);
        doThrow(new ClankChampionshipException("Championship with id [111111111] not found")).when(gameService)
                .editGame(gameDto);

        String res = MAPPER.readValue(mvc.perform(put("/edit-game").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(gameDto))).andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString(), new TypeReference<SimpleWrapper<GameDto>>() {
        }).getMessage();
        assertEquals("Championship with id [111111111] not found", res);
    }

    @Test
    void findGamesByLocationTest() throws Exception {
        LocalDateTime now = LocalDateTime.now();
        GameDto gameDtoNow = createGameDto(now, "Clank House", 1L);
        GameDto gameDtoNowMinusOne = createGameDto(now.minusDays(1), "Clank House", 1L);
        GameDto gameDtoNowPlusOne = createGameDto(now.plusDays(1), "Clank House", 1L);
        when(gameService.findByLocation("Clank House")).thenReturn(List.of(
                gameDtoNow,
                gameDtoNowMinusOne,
                gameDtoNowPlusOne));
        List<GameDto> res = MAPPER.readValue(mvc.perform(
                        get("/find-by-location")
                                .param("location", "Clank House")
                                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(), new TypeReference<CollectionWrapper<GameDto>>() {
        }).getResponseObject();

        assertEquals(3, res.size());
        assertEquals(gameDtoNow, res.stream().filter(gameDto -> gameDto.getGameDate().equals(now)).findFirst().get());
        assertEquals(gameDtoNowMinusOne,
                res.stream().filter(gameDto -> gameDto.getGameDate().equals(now.minusDays(1))).findFirst().get());
        assertEquals(gameDtoNowPlusOne,
                res.stream().filter(gameDto -> gameDto.getGameDate().equals(now.plusDays(1))).findFirst().get());
    }
}
