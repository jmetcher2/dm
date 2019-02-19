package au.id.lagod.jersey_poc.links;

import java.util.HashMap;

import au.id.lagod.entities.BaseDTO;

public class Links extends HashMap<String, String> {

	private static final long serialVersionUID = 1L;
	
	public Links() {
		super();
	}
	
	public <T extends BaseDTO> String put(String key, T dto) {
		return put(key, dto.selfLink);
	}

}
