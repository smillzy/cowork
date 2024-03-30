package tw.appworks.school.example.stylish.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

public class JsonNodeConverter implements AttributeConverter<JsonNode, String> {

    private static final Logger logger = LoggerFactory.getLogger(JsonNodeConverter.class);

    @Override
    public String convertToDatabaseColumn(JsonNode jsonNode) {
        if (jsonNode == null) {
            logger.warn("jsonNode input is null, returning null");
            return null;
        }
        return jsonNode.toPrettyString();
    }

    @Override
    public JsonNode convertToEntityAttribute(String jsonNodeString) {
        if (!StringUtils.hasLength(jsonNodeString)) {
            logger.warn("jsonNodeString input is empty, returning null");
            return null;
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(jsonNodeString);
        } catch (JsonProcessingException e) {
            logger.error("Error parsing jsonNodeString", e);
        }
        return null;
    }

}
