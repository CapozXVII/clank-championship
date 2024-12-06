package it.capozxvii.clankchampionship.model.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import it.capozxvii.clankchampionship.util.Message;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Converter
public class PredictedCharsForGamesConverter implements AttributeConverter<Map<Integer, CharacterEnum>, String> {

    private static final Logger LOG = LoggerFactory.getLogger(PredictedCharsForGamesConverter.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(final Map<Integer, CharacterEnum> otherCategories) {

        String categories;
        try {
            categories = MAPPER.writeValueAsString(otherCategories);
        } catch (final JsonProcessingException e) {
            LOG.error("JSON writing error", e);
            throw new ClankChampionshipException(Message.ERROR_WHILE_CONVERTING_TO_JSON, otherCategories);
        }
        return categories;
    }

    @Override
    public Map<Integer, CharacterEnum> convertToEntityAttribute(final String otherCategoriesJson) {

        Map<Integer, CharacterEnum> otherCategories;
        try {
            otherCategories =
                    MAPPER.readValue(otherCategoriesJson, new TypeReference<HashMap<Integer, CharacterEnum>>() {
                    });
        } catch (final IOException e) {
            LOG.error("JSON reading error", e);
            throw new ClankChampionshipException(Message.ERROR_WHILE_CONVERTING_TO_OBJECT, otherCategoriesJson);
        }
        return otherCategories;
    }
}
