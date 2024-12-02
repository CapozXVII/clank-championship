package it.capozxvii.clankchampionship.model.jpa.converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class PredictedCharsFormGamesConverterTest {

    private static final PredictedCharsForGamesConverter PREDICTED_CHARS_FORM_GAMES =
            new PredictedCharsForGamesConverter();

    @Test
    void convertToDatabaseColumnTest() {
        Map<Integer, CharacterEnum> predictedChars = new HashMap<>();
        predictedChars.put(1, CharacterEnum.BLUE);
        predictedChars.put(2, CharacterEnum.YELLOW);
        predictedChars.put(3, CharacterEnum.GREEN);
        String res = PREDICTED_CHARS_FORM_GAMES.convertToDatabaseColumn(predictedChars);
        assertEquals("""
                             {"1":"BLUE","2":"YELLOW","3":"GREEN"}""", res);
    }

    @Test
    void convertToEntityAttributeTest() {
        Map<Integer, CharacterEnum> predictedChars =
                PREDICTED_CHARS_FORM_GAMES.convertToEntityAttribute(
                        "{\"1\":\"BLUE\",\"2\":\"YELLOW\",\"3\":\"GREEN\"}");
        assertEquals(CharacterEnum.BLUE, predictedChars.get(1));
        assertEquals(CharacterEnum.YELLOW, predictedChars.get(2));
        assertEquals(CharacterEnum.GREEN, predictedChars.get(3));
    }

    @Test
    void convertToEntityAttributeExceptionTest() {
        ClankChampionshipException res = assertThrows(ClankChampionshipException.class,
                                                      () -> PREDICTED_CHARS_FORM_GAMES.convertToEntityAttribute(
                                                              "\"1\":\"BLUE\",\"2\":\"YELLOW\",\"3\":\"GREEN\""));
        assertEquals("Error while converting [\"1\":\"BLUE\",\"2\":\"YELLOW\",\"3\":\"GREEN\"] to Object",
                     res.getMessage());
    }

    @Test
    void convertToDatabaseColumnExceptionTest() {
        PredictedCharsForGamesConverter converter = mock(PredictedCharsForGamesConverter.class);
        Map<Integer, CharacterEnum> predictedChars = new HashMap<>();
        predictedChars.put(1, null);
        predictedChars.put(2, null);
        doThrow(new ClankChampionshipException("Error while converting [{\"1\":null,\"2\":null}] to JSON")).when(
                converter).convertToDatabaseColumn(predictedChars);
        ClankChampionshipException res = assertThrows(ClankChampionshipException.class,
                                                      () -> converter.convertToDatabaseColumn(
                                                              predictedChars));
        assertEquals("Error while converting [{\"1\":null,\"2\":null}] to JSON", res.getMessage());

    }

}
