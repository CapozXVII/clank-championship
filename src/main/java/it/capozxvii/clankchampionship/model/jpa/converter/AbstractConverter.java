package it.capozxvii.clankchampionship.model.jpa.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.capozxvii.clankchampionship.util.Message;
import it.capozxvii.clankchampionship.util.exception.ClankChampionshipException;
import jakarta.persistence.AttributeConverter;
import java.io.IOException;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractConverter<K, V> implements AttributeConverter<Map<K, V>, String> {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractConverter.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public String convertToDatabaseColumn(final Map<K, V> otherCategories) {

        String categories;
        try {
            categories = MAPPER.writeValueAsString(otherCategories);
        } catch (final JsonProcessingException e) {
            LOG.error("JSON writing error", e);
            throw new ClankChampionshipException(Message.ERROR_WHILE_CONVERTING_TO_JSON, otherCategories);
        }
        return categories;
    }

    public Map<K, V> convertToEntityAttribute(final String otherCategoriesJson,
            final TypeReference<Map<K, V>> typeReference) {
        Map<K, V> otherCategories;
        try {
            otherCategories =
                    MAPPER.readValue(otherCategoriesJson, typeReference);
        } catch (final IOException e) {
            LOG.error("JSON reading error", e);
            throw new ClankChampionshipException(Message.ERROR_WHILE_CONVERTING_TO_OBJECT, otherCategoriesJson);
        }
        return otherCategories;
    }

}
