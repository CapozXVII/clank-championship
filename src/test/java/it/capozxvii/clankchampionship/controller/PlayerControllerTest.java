package it.capozxvii.clankchampionship.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import it.capozxvii.clankchampionship.abstracts.AbstractControllerTest;
import it.capozxvii.clankchampionship.model.dto.PlayerDto;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@WebMvcTest(PlayerController.class)
class PlayerControllerTest extends AbstractControllerTest {

    @Test
    void insertPlayerTest() throws Exception {
        PlayerDto playerDto = createPlayerDto("capoz", "Cri Cap", null);
        when(playerService.insertPlayer(playerDto)).thenAnswer(im -> {
            playerDto.setId(1L);
            return playerDto;
        });
        String res = mvc.perform(post("/insert-player").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(playerDto))).andExpect(status().isCreated()).andReturn()
                .getResponse().getContentAsString();
        assertEquals("Player with id [capoz] has been saved", res);
    }

    @Test
    void insertPlayerExceptionTest() throws Exception {
        PlayerDto playerDto = createPlayerDto("capoz", "Cri Cap", null);
        ClankChampionshipException exception = new ClankChampionshipException("Unknown error", "");
        when(playerService.insertPlayer(playerDto)).thenThrow(exception);
        String res = mvc.perform(post("/insert-player").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(playerDto))).andExpect(status().isInternalServerError())
                .andReturn()
                .getResponse().getContentAsString();
        assertEquals("Unknown error", res);
    }

    @Test
    void getPlayerTest() throws Exception {
        PlayerID playerID = PlayerID.builder().nickname("capoz").id(1L).build();
        when(playerService.getPlayerById(playerID)).thenReturn(createPlayerDto("capoz", "Cri Cap", 1L));
        PlayerDto res = MAPPER.readValue(mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(playerID)))
                .andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString(), new TypeReference<SimpleWrapper<PlayerDto>>() {
        }).getResponseObject();

        assertEquals("capoz", res.getNickname());
        assertEquals(1L, res.getId());
        assertEquals("Cri Cap", res.getFullname());
    }

    @Test
    void getPlayerExceptionTest() throws Exception {
        ClankChampionshipException exception =
                new ClankChampionshipException("Player with id [1L, nickname capoz] not found", "");
        PlayerID playerID = PlayerID.builder().nickname("capoz").id(1L).build();
        when(playerService.getPlayerById(playerID)).thenThrow(exception);
        String res = MAPPER.readValue(mvc.perform(get("/").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(playerID)))
                .andExpect(status().isInternalServerError()).andReturn()
                .getResponse().getContentAsString(), new TypeReference<SimpleWrapper<PlayerDto>>() {
        }).getMessage();
        assertEquals("Player with id [1L, nickname capoz] not found", res);
    }

    @Test
    void deletePlayerTest() throws Exception {
        PlayerID playerID = PlayerID.builder().nickname("capoz").id(1L).build();
        doNothing().when(playerService).deletePlayer(playerID);
        mvc.perform(delete("/delete-player").contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(MAPPER.writeValueAsString(playerID))).andExpect(status().isOk());
    }
}
