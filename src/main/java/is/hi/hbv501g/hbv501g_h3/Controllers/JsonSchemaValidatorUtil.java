package is.hi.hbv501g.hbv501g_h3.Controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersion;
import com.networknt.schema.ValidationMessage;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class JsonSchemaValidatorUtil {

    private static final JsonSchemaFactory schemaFactory = JsonSchemaFactory.getInstance(SpecVersion.VersionFlag.V7);

    public static Map<String, String> validateJson(String json, String schemaPath) throws Exception {
        // Load JSON schema from classpath
        InputStream schemaStream = JsonSchemaValidatorUtil.class.getClassLoader().getResourceAsStream(schemaPath);
        if (schemaStream == null) {
            throw new IllegalArgumentException("Schema file not found: " + schemaPath);
        }

        JsonSchema schema = schemaFactory.getSchema(schemaStream);

        // Parse the incoming JSON string
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // Validate JSON against the schema
        Set<ValidationMessage> validationMessages = schema.validate(jsonNode);

        // Create a map to store any validation errors
        Map<String, String> errorMap = new HashMap<>();
        for (ValidationMessage validationMessage : validationMessages) {
            errorMap.put(validationMessage.getPath(), validationMessage.getMessage());
        }

        // Debugging output to log validation errors
        if (!errorMap.isEmpty()) {
            System.out.println("Validation Errors Found: " + errorMap);
        } else {
            System.out.println("No Validation Errors");
        }

        return errorMap; // Return any errors found
    }
}
