package cn.edu.xjtlu.readingnotes;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.junit.jupiter.api.Assertions;

@SpringBootTest
class ApiContractTest {

    @Autowired
    private OpenAPI springOpenApi; 
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    void shouldGenerateValidOpenAPISpec() throws Exception {
        // Directly use the injected OpenAPI object
        String openApiSpec = objectMapper.writeValueAsString(springOpenApi);
        
        // Validate using Swagger parser
        OpenAPI parsedOpenApi = new OpenAPIV3Parser().readContents(openApiSpec).getOpenAPI();
        
        // Add validation assertions
        Assertions.assertNotNull(parsedOpenApi, "OpenAPI specification parsing failed");
        Assertions.assertNotNull(parsedOpenApi.getInfo(), "Missing API basic information");
        Assertions.assertFalse(parsedOpenApi.getPaths().isEmpty(), "API paths not generated correctly");
    }
}