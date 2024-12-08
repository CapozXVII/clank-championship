package it.capozxvii.clankchampionship.model.jpa.converter;

import com.fasterxml.jackson.core.type.TypeReference;
import it.capozxvii.clankchampionship.model.enums.CharacterEnum;
import jakarta.persistence.Converter;
import java.util.Map;

@Converter
public class PredictedCharsForGamesConverter extends AbstractConverter<Integer, CharacterEnum> {
    public String convertToDatabaseColumn(final Map<Integer, CharacterEnum> otherCategories) {
        return super.convertToDatabaseColumn(otherCategories);
    }

    public Map<Integer, CharacterEnum> convertToEntityAttribute(final String otherCategoriesJson) {
        return super.convertToEntityAttribute(otherCategoriesJson, new TypeReference<>() {
        });
    }
}
