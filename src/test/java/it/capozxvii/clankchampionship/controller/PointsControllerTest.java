package it.capozxvii.clankchampionship.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.type.TypeReference;
import it.capozxvii.clankchampionship.abstracts.AbstractControllerTest;
import it.capozxvii.clankchampionship.model.dto.PointsDto;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import it.capozxvii.clankchampionship.util.wrapper.SimpleWrapper;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;

@WebMvcTest(PointsController.class)
class PointsControllerTest extends AbstractControllerTest {

    @Test
    void insertPointsTest() throws Exception {
        List<PointsDto> pointsDtoList = new ArrayList<>();
        pointsDtoList.add(createPointsDto(17, 6, 8, 0, 5, 42, 10, 3, 19, 0, 18, 9, 12, 20, null, CharacterEnum.BLUE,
                createPlayerDto("Capoz", "Cri Cap", null)));
        pointsDtoList.add(createPointsDto(27, 0, 0, 0, 5, 14, 4, 0, 21, 0, 16, 10, 12, 20, null, CharacterEnum.GREY,
                createPlayerDto("Ludovick", "Lud", null)));
        pointsDtoList.add(createPointsDto(5, 0, 0, 0, 0, 14, 8, 0, 19, 0, 44, 0, 0, 20, null, CharacterEnum.YELLOW,
                createPlayerDto("Ganj", "Ivan", null)));
        Long gameId = 1L;
        Long championshipId = 1L;

        doNothing().when(pointsService).insertPoints(pointsDtoList, gameId, championshipId);

        String res = mvc.perform(post("/insert-points").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(pointsDtoList)).param("gameId", String.valueOf(gameId))
                        .param("championshipId", String.valueOf(championshipId))).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();
        verify(pointsService, times(1)).insertPoints(pointsDtoList, gameId, championshipId);

        assertEquals("Points have been saved", res);
    }

    @Test
    void insertPointsChampionshipNotExisting() throws Exception {

        List<PointsDto> pointsDtoList = new ArrayList<>();
        pointsDtoList.add(createPointsDto(17, 6, 8, 0, 5, 42, 10, 3, 19, 0, 18, 9, 12, 20, null, CharacterEnum.BLUE,
                createPlayerDto("Capoz", "Cri Cap", null)));
        pointsDtoList.add(createPointsDto(27, 0, 0, 0, 5, 14, 4, 0, 21, 0, 16, 10, 12, 20, null, CharacterEnum.GREY,
                createPlayerDto("Ludovick", "Lud", null)));
        pointsDtoList.add(createPointsDto(5, 0, 0, 0, 0, 14, 8, 0, 19, 0, 44, 0, 0, 20, null, CharacterEnum.YELLOW,
                createPlayerDto("Ganj", "Ivan", null)));
        Long gameId = 1L;
        Long championshipId = 15454554L;

        doThrow(new ClankChampionshipException("Championship with id 15454554 not found")).when(pointsService)
                .insertPoints(pointsDtoList, gameId, championshipId);

        String res = mvc.perform(post("/insert-points").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(pointsDtoList)).param("gameId", String.valueOf(gameId))
                        .param("championshipId", String.valueOf(championshipId)))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();
        verify(pointsService, times(1)).insertPoints(pointsDtoList, gameId, championshipId);

        assertEquals("Championship with id 15454554 not found", res);
    }

    @Test
    void insertPointsPlayerNotExistingTest() throws Exception {
        List<PointsDto> pointsDtoList = new ArrayList<>();
        pointsDtoList.add(createPointsDto(17, 6, 8, 0, 5, 42, 10, 3, 19, 0, 18, 9, 12, 20, null, CharacterEnum.BLUE,
                createPlayerDto("Capoz", "Cri Cap", 17L)));
        pointsDtoList.add(createPointsDto(27, 0, 0, 0, 5, 14, 4, 0, 21, 0, 16, 10, 12, 20, null, CharacterEnum.GREY,
                createPlayerDto("Ludovick", "Lud", null)));
        pointsDtoList.add(createPointsDto(5, 0, 0, 0, 0, 14, 8, 0, 19, 0, 44, 0, 0, 20, null, CharacterEnum.YELLOW,
                createPlayerDto("Ganj", "Ivan", null)));

        Long gameId = 1L;
        Long championshipId = 1L;

        doThrow(new ClankChampionshipException("Player with id 17, nickname Capoz not found")).when(pointsService)
                .insertPoints(pointsDtoList, gameId, championshipId);

        String res = mvc.perform(post("/insert-points").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(pointsDtoList)).param("gameId", String.valueOf(gameId))
                        .param("championshipId", String.valueOf(championshipId)))
                .andExpect(status().isInternalServerError())
                .andReturn().getResponse().getContentAsString();
        verify(pointsService, times(1)).insertPoints(pointsDtoList, gameId, championshipId);

        assertEquals("Player with id 17, nickname Capoz not found", res);
    }

    @Test
    void updatePointsTest() throws Exception {
        PointsDto updating = createPointsDto(17, 6, 8, 0, 5, 42, 10, 3, 19, 0, 18, 9, 12, 20, null,
                CharacterEnum.BLUE,
                createPlayerDto("Capoz", "Cri Cap", 17L));
        updating.setId(10L);

        when(pointsService.updatePoints(updating)).thenReturn(updating);

        PointsDto res = MAPPER.readValue(mvc.perform(put("/update-points").contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(MAPPER.writeValueAsString(updating))).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString(), new TypeReference<SimpleWrapper<PointsDto>>() {
        }).getResponseObject();

        assertEquals(updating, res);
    }
}
