package dto;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum CareToolParser {
	
	INSTANCE;
	
	private static ObjectMapper mapper = new ObjectMapper();
	
	public static List<CTExample> parseExample(String json) throws JsonParseException, JsonMappingException, IOException{		
		return mapper.readValue(json.toString(), new TypeReference<List<CTExample>>(){});		
	}
	
	public static List<CTItem> parseItem(String json) throws JsonParseException, JsonMappingException, IOException{		
		return mapper.readValue(json.toString(), new TypeReference<List<CTItem>>(){});		
	}
	
	public static List<CTMiniItem> parseItems(String json) throws JsonParseException, JsonMappingException, IOException{		
		return mapper.readValue(json.toString(), new TypeReference<List<CTMiniItem>>(){});		
	}

}
