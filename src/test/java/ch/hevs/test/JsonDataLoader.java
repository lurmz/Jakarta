package ch.hevs.test;


import java.io.InputStream;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JsonDataLoader {
	
	//helper class and method to load the ressource
	@SuppressWarnings("unchecked")
	public static Map<String, Object> loadJsonData(String filePath) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream inputStream = JsonDataLoader.class.getClassLoader().getResourceAsStream(filePath)) {
            if (inputStream == null) {
                throw new IllegalArgumentException("File not found: " + filePath);
            }
            return mapper.readValue(inputStream, Map.class);
        } catch (Exception e) {
        	e.getMessage();
            e.printStackTrace();
            return null;
        }
    }
	
}
