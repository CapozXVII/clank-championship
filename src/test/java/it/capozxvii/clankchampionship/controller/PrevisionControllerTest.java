package it.capozxvii.clankchampionship.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import it.capozxvii.clankchampionship.abstracts.AbstractControllerTest;
import it.capozxvii.clankchampionship.model.dto.PrevisionDto;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.model.jpa.compositekeys.PlayerID;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.CollectionWrapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@WebMvcTest(PrevisionController.class)
class PrevisionControllerTest extends AbstractControllerTest {

    @Test
    void insertPrevisionsTest() throws Exception {
        List<PrevisionDto> previsionDtoList = new ArrayList<>();
        Map<Integer, CharacterEnum> characters = new HashMap<>();
        characters.put(1, CharacterEnum.BLUE);
        characters.put(2, CharacterEnum.YELLOW);
        characters.put(3, CharacterEnum.GREY);
        PrevisionDto previsionDtoCapoz = createPrevisionDto(1L,
                PlayerID.builder().nickname("Capoz").id(1L).build(), characters);
        previsionDtoList.add(previsionDtoCapoz);

        characters = new HashMap<>();
        characters.put(1, CharacterEnum.YELLOW);
        characters.put(2, CharacterEnum.GREY);
        characters.put(3, CharacterEnum.BLUE);

        PrevisionDto previsionDtoLudovick =
                createPrevisionDto(1L, PlayerID.builder().nickname("Ludovick").id(2L).build(), characters);
        previsionDtoList.add(previsionDtoLudovick);

        previsionDtoCapoz.setId(1L);
        previsionDtoLudovick.setId(2L);
        List<PrevisionDto> prevRes = List.of(previsionDtoCapoz, previsionDtoLudovick);
        when(previsionService.insertPrevisions(previsionDtoList)).thenReturn(prevRes);

        List<PrevisionDto> res =
                MAPPER.readValue(mvc.perform(post("/insert-previsions").contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(previsionDtoList)))
                        .andExpect(status().isOk()).andReturn()
                        .getResponse().getContentAsString(), new TypeReference<CollectionWrapper<PrevisionDto>>() {
                }).getResponseObject();

        assertEquals(prevRes, res);
    }

    @Test
    void insertPrevisionsPlayerNotExistingTest() throws Exception {
        List<PrevisionDto> previsionDtoList = new ArrayList<>();
        Map<Integer, CharacterEnum> characters = new HashMap<>();
        characters.put(1, CharacterEnum.BLUE);
        characters.put(2, CharacterEnum.YELLOW);
        characters.put(3, CharacterEnum.GREY);
        PrevisionDto previsionDtoCapoz = createPrevisionDto(1L,
                PlayerID.builder().nickname("Capoz").id(1L).build(), characters);
        previsionDtoList.add(previsionDtoCapoz);
        doThrow(new ClankChampionshipException("Player with id [1L, nickname Capoz] not found")).when(
                previsionService).insertPrevisions(previsionDtoList);

        String res =
                MAPPER.readValue(mvc.perform(post("/insert-previsions").contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(previsionDtoList)))
                        .andExpect(status().isInternalServerError()).andReturn()
                        .getResponse().getContentAsString(), new TypeReference<CollectionWrapper<PrevisionDto>>() {
                }).getMessage();

        assertEquals("Player with id [1L, nickname Capoz] not found", res);

    }

    @Test
    void insertPrevisionsChampionshipNotExistingTest() throws Exception {
        List<PrevisionDto> previsionDtoList = new ArrayList<>();
        Map<Integer, CharacterEnum> characters = new HashMap<>();
        characters.put(1, CharacterEnum.BLUE);
        characters.put(2, CharacterEnum.YELLOW);
        characters.put(3, CharacterEnum.GREY);
        PrevisionDto previsionDtoCapoz = createPrevisionDto(100L,
                PlayerID.builder().nickname("Capoz").id(1L).build(), characters);
        previsionDtoList.add(previsionDtoCapoz);
        doThrow(new ClankChampionshipException("Championship with id 100 not found")).when(
                previsionService).insertPrevisions(previsionDtoList);

        String res =
                MAPPER.readValue(mvc.perform(post("/insert-previsions").contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(MAPPER.writeValueAsString(previsionDtoList)))
                        .andExpect(status().isInternalServerError()).andReturn()
                        .getResponse().getContentAsString(), new TypeReference<CollectionWrapper<PrevisionDto>>() {
                }).getMessage();

        assertEquals("Championship with id 100 not found", res);

    }
}
